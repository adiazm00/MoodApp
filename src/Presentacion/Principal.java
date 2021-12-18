package Presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.json.JSONException;
import org.json.JSONObject;

import Dominio.GestorPlaylist;
import Dominio.Usuario;

import java.awt.Toolkit;
import java.io.IOException;
import java.awt.CardLayout;

public class Principal {
	String APIkey = "2f3043f9f6a0d2c127fe9656e1b335c2";
	private JFrame frameApp;
	private Usuario usuario;
	JSONObject JSONTiempo;
	Login login = new Login(this);
	SeleccionarPlaylist seleccionarPlaylist;
	ResultadoPlaylist resultadoPlaylist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frameApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws JSONException
	 * @throws IOException 
	 */
	public Principal() throws JSONException, IOException {
		JSONTiempo = GestorPlaylist.leerAPITiempo("Ciudad%20Real", APIkey);
		seleccionarPlaylist = new SeleccionarPlaylist(this, JSONTiempo);
		resultadoPlaylist = new ResultadoPlaylist(this, JSONTiempo);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameApp = new JFrame();
		frameApp.setTitle("MoodApp");
		frameApp.setResizable(false);
		//frameApp.setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/Recursos/moodapp.png")));
		frameApp.setBounds(300, 100, 900, 650);
		frameApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameApp.getContentPane().setLayout(new CardLayout(0, 0));
		frameApp.getContentPane().add(login);
		frameApp.getContentPane().add(seleccionarPlaylist);
		frameApp.getContentPane().add(resultadoPlaylist);

	}

	public void inicioSesionPulsado(Usuario usuario) {
		this.usuario = usuario;
		GestorPlaylist.getToken();
		login.setVisible(false);
		resultadoPlaylist.setVisible(false);
		seleccionarPlaylist.setVisible(true);
	}

	public void salirPulsado() {
		login.setVisible(true);
		seleccionarPlaylist.setVisible(false);
	}

	public void seleccionarCancionesPulsado(double temperatura, String tipoDia) {
		seleccionarPlaylist.setVisible(false);
		login.setVisible(false);
		resultadoPlaylist.setVisible(true);
		resultadoPlaylist.setTipoDia(temperatura, tipoDia);
	}

	public void atrasPulsado() {
		login.setVisible(false);
		seleccionarPlaylist.setVisible(true);
		resultadoPlaylist.setVisible(false);
	}

	public Usuario getUsuario() {
		return this.usuario;
	}
}
