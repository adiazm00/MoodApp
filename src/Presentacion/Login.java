package Presentacion;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.json.JSONException;

import Dominio.GestorUsuario;
import Dominio.Usuario;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JPanel {
	private JLabel lblLogo;
	private JLabel lblUsuario;
	private JLabel lblContrasena;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	private JButton btnIniciarSesion;
	private JLabel lblFondo;

	private Usuario usuario;
	private Principal principal;

	/**
	 * Create the panel.
	 */
	public Login(Principal principal) {
		this.principal = principal;
		setBounds(new Rectangle(0, 0, 900, 650));
		setLayout(null);
		{
			lblLogo = new JLabel("");
			lblLogo.setBounds(335, 73, 200, 200);
			try {
				Image imagenOriginal = ImageIO.read(Login.class.getResource("/recursos/moodapp.png"));
				Image imagenEscalada = imagenOriginal.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
						java.awt.Image.SCALE_SMOOTH);
				ImageIcon iconoLabel = new ImageIcon(imagenEscalada);
				lblLogo.setIcon(iconoLabel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			add(lblLogo);
		}
		{
			lblUsuario = new JLabel("Usuario");
			lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
			lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 22));
			lblUsuario.setBounds(230, 291, 142, 40);
			add(lblUsuario);
		}
		{
			lblContrasena = new JLabel("Contraseña");
			lblContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
			lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 22));
			lblContrasena.setBounds(230, 353, 142, 40);
			add(lblContrasena);
		}
		{
			txtUsuario = new JTextField();
			txtUsuario.setBorder(new LineBorder(Color.BLACK, 1, true));
			txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtUsuario.setBounds(386, 291, 257, 40);
			add(txtUsuario);
		}
		{
			txtContrasena = new JPasswordField();
			txtContrasena.setBorder(new LineBorder(Color.BLACK, 1, true));
			txtContrasena.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtContrasena.setBounds(386, 353, 257, 40);
			add(txtContrasena);
		}
		{
			btnIniciarSesion = new JButton("Iniciar sesión");
			btnIniciarSesion.addActionListener(new BtnIniciarSesionActionListener());
			btnIniciarSesion.setBorder(new LineBorder(Color.BLACK, 1, true));
			btnIniciarSesion.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnIniciarSesion.setBounds(487, 404, 156, 33);
			add(btnIniciarSesion);
		}
		{
			lblFondo = new JLabel("");
			lblFondo.setBounds(0, 0, 900, 650);
			try {
				Image imagenOriginal = ImageIO.read(Login.class.getResource("/recursos/Fondo.jpg"));
				Image imagenEscalada = imagenOriginal.getScaledInstance(lblFondo.getWidth(), lblFondo.getHeight(),
						java.awt.Image.SCALE_SMOOTH);
				ImageIcon iconoLabel = new ImageIcon(imagenEscalada);
				lblFondo.setIcon(iconoLabel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			add(lblFondo);
		}

	}

	private class BtnIniciarSesionActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (!txtUsuario.getText().isEmpty() && txtContrasena.getPassword().length != 0) {
				try {
					usuario = GestorUsuario.comprobarUsuario(txtUsuario.getText().toString(),
							String.valueOf(txtContrasena.getPassword()));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (usuario == null) {
					JOptionPane.showMessageDialog(null, "Datos incorrectos.", "ERROR AL INICIAR SESIÓN", JOptionPane.INFORMATION_MESSAGE);
				} else {
					principal.inicioSesionPulsado(usuario);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debe rellenar los campos anteriores.", "ERROR AL INICIAR SESIÓN", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
