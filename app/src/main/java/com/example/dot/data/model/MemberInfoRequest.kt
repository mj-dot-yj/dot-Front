package com.example.dot.data.model

import java.util.regex.Pattern

data class MemberInfoRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val checkPassword : String? = null,
    val phone: String? = null
) {
    fun checkValidation(): String {
        if (!checkName()) {
            return "이름을 형식에 맞게 입력해주세요."
        }
        if (!checkEmail()) {
            return "이메일을 형식에 맞게 입력해주세요."
        }
        if (!checkPassword()) {
            return "비밀번호를 형식에 맞게 입력해주세요."
        }
        if (!matchPassword()) {
            return "비밀번호가 일치하지 않습니다."
        }
        if (!checkPhone()) {
            return "휴대폰 번호를 형식에 맞게 입력해주세요."
        }
        return ""
    }

    private fun checkName(): Boolean {
        if (this.name!!.isBlank()) {
            return false
        }
        return true
    }

    private fun checkEmail(): Boolean {
        val emailPattern =
            "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
        if (this.email!!.isBlank() || !Pattern.matches(emailPattern, this.email)) {
            return false
        }
        return true
    }

    private fun checkPassword(): Boolean {
        val pwPattern = "^[A-Za-z0-9]{6,12}$"
        if (this.password!!.isBlank() || !Pattern.matches(pwPattern, this.password)) {
            return false
        }
        return true
    }

    private fun matchPassword() : Boolean {
        if (!this.password.equals(this.checkPassword)){
            return false
        }
        return true
    }

    private fun checkPhone(): Boolean {
        val phonePattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"
        if (this.phone!!.isBlank() || !Pattern.matches(phonePattern, this.phone)) {
            return false
        }
        return true
    }
}
