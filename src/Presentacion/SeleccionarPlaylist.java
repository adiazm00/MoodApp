package Presentacion;

import javax.swing.JPanel;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;
import java.net.URI;

import org.json.JSONException;
import org.json.*;

import Dominio.GestorPlaylist;
import Dominio.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;

public class SeleccionarPlaylist extends JPanel {

	private Usuario usuario;
	private JLabel lblLogo;
	private JLabel lblTipoDia;
	private JLabel lblCiudad;
	private JTextField txtTipoDia;
	private JTextField txtCiudad;
	private JButton btnSalir;
	private JLabel lblFondo;
	private JButton btnSeleccionarCanciones;
	private Principal principal;
	JSONObject JSONTiempo;
	JSONArray JSONPlaylist;

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public SeleccionarPlaylist(Principal principal, JSONObject JSONTiempo) throws IOException {
		this.principal = principal;
		this.JSONTiempo = JSONTiempo;
		setLayout(null);
		setBounds(0, 0, 900, 650);

		lblLogo = new JLabel("");
		lblLogo.setBounds(335, 73, 200, 200);
		try {
			Image imagenOriginal = ImageIO.read(SeleccionarPlaylist.class.getResource("/recursos/moodapp.png"));
			Image imagenEscalada = imagenOriginal.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			ImageIcon iconoLabel = new ImageIcon(imagenEscalada);
			lblLogo.setIcon(iconoLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		add(lblLogo);

		btnSeleccionarCanciones = new JButton("Seleccionar canciones");
		btnSeleccionarCanciones.addActionListener(new BtnSeleccionarCancionesActionListener());
		btnSeleccionarCanciones.setBounds(487, 404, 156, 33);
		btnSeleccionarCanciones.setBorder(new LineBorder(Color.BLACK, 1, true));
		btnSeleccionarCanciones.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(btnSeleccionarCanciones);

		lblTipoDia = new JLabel("Tipo de día ");
		lblTipoDia.setBounds(181, 291, 195, 40);
		lblTipoDia.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTipoDia.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTipoDia);

		lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(70, 353, 306, 40);
		lblCiudad.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCiudad.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblCiudad);

		txtTipoDia = new JTextField();
		txtTipoDia.setEditable(false);
		txtTipoDia.setBounds(386, 291, 257, 40);
		txtTipoDia.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtTipoDia.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(txtTipoDia);
		txtTipoDia.setColumns(10);

		txtCiudad = new JTextField();
		txtCiudad.setEditable(false);
		txtCiudad.setText("Ciudad Real");
		txtCiudad.setBounds(386, 353, 257, 40);
		txtCiudad.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtCiudad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(txtCiudad);
		txtCiudad.setColumns(10);

		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new BtnSalirActionListener());
		btnSalir.setBounds(24, 506, 128, 33);
		btnSalir.setBorder(new LineBorder(Color.BLACK, 1, true));
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(btnSalir);

		lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 900, 650);
		try {
			Image imagenOriginal = ImageIO.read(SeleccionarPlaylist.class.getResource("/recursos/Fondo.jpg"));
			Image imagenEscalada = imagenOriginal.getScaledInstance(lblFondo.getWidth(), lblFondo.getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			ImageIcon iconoLabel = new ImageIcon(imagenEscalada);
			lblFondo.setIcon(iconoLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		add(lblFondo);
		JSONPlaylist = GestorPlaylist.leerPlaylist();
		txtTipoDia.setText(cambiarTipoDia(JSONTiempo.getJSONArray("weather").getJSONObject(0).getString("main")));
	}

	public void setUsuario(Usuario u) throws JSONException {
		usuario = u;
	}

	private class BtnSalirActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			principal.salirPulsado();
		}
	}

	private class BtnSeleccionarCancionesActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!txtCiudad.getText().isEmpty())
				principal.seleccionarCancionesPulsado(JSONTiempo.getJSONObject("main").getDouble("temp") - 273,
						cambiarTipoDia(JSONTiempo.getJSONArray("weather").getJSONObject(0).getString("main")));
			else
				JOptionPane.showMessageDialog(null, "Indique la playlist.");
		}
	}

	public String cambiarTipoDia(String tDia) {
		String tDiaFinal = "";
		switch (tDia) {
		case "Thunderstorm":
			tDiaFinal = "Tormenta";
			break;
		case "Drizzle":
			tDiaFinal = "Llovizna";
			break;
		case "Rain":
			tDiaFinal = "Lluvia";
			break;
		case "Snow":
			tDiaFinal = "Nieve";
			break;
		case "Mist":
			tDiaFinal = "Neblina";
			break;
		case "Smoke":
			tDiaFinal = "Humo";
			break;
		case "Haze":
			tDiaFinal = "Bruma";
			break;
		case "Dust":
			tDiaFinal = "Polvo";
			break;
		case "Fog":
			tDiaFinal = "Niebla";
			break;
		case "Sand":
			tDiaFinal = "Chubascos";
			break;
		case "Ash":
			tDiaFinal = "Ceniza";
			break;
		case "Squall":
			tDiaFinal = "Borrasca";
			break;
		case "Tornado":
			tDiaFinal = "Tornado";
			break;
		case "Clear":
			tDiaFinal = "Despejado";
			break;
		case "Clouds":
			tDiaFinal = "Nublado";
			break;
		}
		return tDiaFinal;
	}
}
