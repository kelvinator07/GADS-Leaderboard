package com.geekykel.gads.ui.main

import android.app.Application
import android.util.Patterns
import android.webkit.URLUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.geekykel.gads.R
import com.geekykel.gads.model.ResultWrapper
import com.geekykel.gads.model.UserModel
import com.geekykel.gads.repository.userRepository
import kotlinx.coroutines.launch

class SubmitViewModel(application: Application) : AndroidViewModel(application) {

    private val _user: MutableLiveData<UserModel> = MutableLiveData(UserModel())
    val user: LiveData<UserModel> = _user

    private val _errorMsg: MutableLiveData<String> = MutableLiveData()
    val errorMsg: LiveData<String> = _errorMsg

    private val _showConfirmationDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val showConfirmationDialog: LiveData<Boolean> = _showConfirmationDialog

    private val _showSuccessDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val showSuccessDialog: LiveData<Boolean> = _showSuccessDialog

    private val _showFailDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val showFailDialog: LiveData<Boolean> = _showFailDialog

    private val _submitStatus: MutableLiveData<ResultWrapper<Any>> = MutableLiveData()
    val submitStatus: LiveData<ResultWrapper<Any>> = _submitStatus


    fun submit() {
        if (validInput()) {
            if (_showConfirmationDialog.value!!) {
                viewModelScope.launch {
                    userRepository.submit(user.value!!, _submitStatus)
                }
            } else {
                _showConfirmationDialog.value = true
            }
        }
    }

    fun showConfirmationDialog(isShow: Boolean) {
        _showConfirmationDialog.value = isShow
    }

    fun showFailDialog(isShow: Boolean) {
        _showFailDialog.value = isShow
    }

    fun showSuccessDialog(isShow: Boolean) {
        _showSuccessDialog.value = isShow
    }

    private fun validInput(): Boolean {
        val currentUser = user.value
        val noEmptyField = currentUser != null &&
                currentUser.firstName.isNotBlank() &&
                currentUser.lastName.isNotBlank() &&
                currentUser.email.isNotBlank() &&
                currentUser.projectLink.isNotBlank()

        if (!noEmptyField) {
            _errorMsg.value =
                getApplication<Application>().resources.getString(R.string.all_fields_required)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(currentUser!!.email).matches()) {
            _errorMsg.value =
                getApplication<Application>().resources.getString(R.string.invalid_email)

            return false
        }

        if (!URLUtil.isValidUrl(currentUser.projectLink)) {
            _errorMsg.value =
                getApplication<Application>().resources.getString(R.string.invalid_link)

            return false
        }

        return true
    }
}