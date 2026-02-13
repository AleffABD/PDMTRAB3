package com.example.todolist

import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


//codigo abaixo gerado por llm
private fun traduzirErroFirebase(e: Exception): String {
    if (e is FirebaseAuthException) {
        return when (e.errorCode) {
            "ERROR_INVALID_EMAIL" ->
                "O e-mail informado é inválido."

            "ERROR_USER_NOT_FOUND" ->
                "Usuário não encontrado. Verifique o e-mail."

            "ERROR_WRONG_PASSWORD" ->
                "Senha incorreta. Tente novamente."

            "ERROR_EMAIL_ALREADY_IN_USE" ->
                "Este e-mail já está cadastrado."

            "ERROR_WEAK_PASSWORD" ->
                "A senha é fraca. Use pelo menos 6 caracteres."

            "ERROR_TOO_MANY_REQUESTS" ->
                "Muitas tentativas. Aguarde um pouco e tente novamente."

            "ERROR_NETWORK_REQUEST_FAILED" ->
                "Falha de conexão. Verifique a internet e tente novamente."

            else ->
                "Não foi possível autenticar. Tente novamente."
        }
    }
    return "Erro inesperado. Tente novamente."
}

@Composable
fun AuthGate(
    onAuthenticated: @Composable (onLogout: () -> Unit) -> Unit
) {
    val auth = remember { FirebaseAuth.getInstance() }

    var screen by remember { mutableStateOf("login") }
    var loggedIn by remember { mutableStateOf(auth.currentUser != null) }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    if (loggedIn) {
        val onLogout = {
            auth.signOut()
            loggedIn = false
            screen = "login"
            error = null
            loading = false
        }

        onAuthenticated(onLogout)
        return
    }

    when (screen) {
        "login" -> LoginScreen(
            onLoginClick = { email, password ->
                if (email.isBlank() || password.isBlank()) {
                    error = "Preencha e-mail e senha."
                    return@LoginScreen
                }
                loading = true
                error = null
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener { loggedIn = true }
                    .addOnFailureListener { error = traduzirErroFirebase(it) }
                    .addOnCompleteListener { loading = false }
            },
            onGoToSignup = {
                error = null
                screen = "signup"
            },
            errorMessage = error,
            loading = loading
        )

        "signup" -> SignupScreen(
            onSignupClick = { email, password ->
                if (email.isBlank() || password.isBlank()) {
                    error = "Preencha e-mail e senha."
                    return@SignupScreen
                }
                if (password.length < 6) {
                    error = "A senha deve ter pelo menos 6 caracteres."
                    return@SignupScreen
                }
                loading = true
                error = null
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { loggedIn = true }
                    .addOnFailureListener { error = traduzirErroFirebase(it) }
                    .addOnCompleteListener { loading = false }
            },
            onGoToLogin = {
                error = null
                screen = "login"
            },
            errorMessage = error,
            loading = loading
        )
    }
}
