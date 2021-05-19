package br.com.chicorialabs.picpayclonekt.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.chicorialabs.picpayclonekt.data.Login
import br.com.chicorialabs.picpayclonekt.data.Usuario
import br.com.chicorialabs.picpayclonekt.data.UsuarioLogado
import br.com.chicorialabs.picpayclonekt.databinding.FragmentLoginBinding
import br.com.chicorialabs.picpayclonekt.extension.getString
import br.com.chicorialabs.picpayclonekt.ui.componente.ComponenteViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

//    companion object {
//        fun newInstance() = LoginFragment()
//    }

    private val mLoginViewModel: LoginViewModel by sharedViewModel()
    private val componenteViewModel: ComponenteViewModel by sharedViewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.loginViewModel = mLoginViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componenteViewModel.atualizaComponentes(bottomNavigation = false)

        observaToken()
        observaProgressBar()
        observaErro()
        observaLogin()
    }

    private fun observaErro() {
        mLoginViewModel.onError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro de autenticação", Toast.LENGTH_LONG)
        }
    }

    private fun observaProgressBar() {
        mLoginViewModel.onLoading.observe(viewLifecycleOwner) { onLoading ->
            if (onLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun observaToken() {
        mLoginViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.i("picpay_kt", "onViewCreated: token modificado! ${it.token}")
                vaiParaHome()
            }
        }
    }

    private fun observaLogin() {
        mLoginViewModel.efetuouLogin.observe(viewLifecycleOwner, Observer<Boolean> { efetuouLogin ->
            if (efetuouLogin) {
                val usuario = binding.textInputUsuario.getString()
                val senha = binding.textInputSenha.getString()
                val login = Login(usuario = usuario, senha = senha)
                mLoginViewModel.login(login)
                UsuarioLogado.usuario = Usuario(usuario)
            }
        })
    }

    private fun vaiParaHome() {
        val direcao = LoginFragmentDirections.actionLoginFragmentToNavigationHome()
        val controlador = findNavController()
        controlador.navigate(direcao)
    }

}