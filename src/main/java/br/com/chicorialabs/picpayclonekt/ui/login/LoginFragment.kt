package br.com.chicorialabs.picpayclonekt.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.chicorialabs.picpayclonekt.data.Usuario
import br.com.chicorialabs.picpayclonekt.data.UsuarioLogado
import br.com.chicorialabs.picpayclonekt.databinding.FragmentLoginBinding
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

        mLoginViewModel.efetuouLogin.observe(viewLifecycleOwner, Observer<Boolean> { efetuouLogin ->
            if(efetuouLogin) {
                val login = binding.editTextUsuario.text.toString()
                UsuarioLogado.usuario = Usuario(login)
                vaiParaHome()
            }
        })
    }

    private fun vaiParaHome() {
        val direcao = LoginFragmentDirections.actionLoginFragmentToNavigationHome()
        val controlador = findNavController()
        controlador.navigate(direcao)
    }

}