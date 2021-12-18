package Dominio;

import java.io.BufferedReader;

import java.util.Base64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GestorPlaylist {

	static String token = "BQA7KyyAFsfr54rdN-f_aleQ5gwl8BPZ_ObsPn9Fag787rZtj9ROdLsAqs4pWZOogdE25puqULFuh6HukJpHgJga2g7vrZ1z8V_KHeEYwowEZUGnSpapUzwSWcE0jdYgKYTUsQUbKMUHIFqV1uqsHJByYtA0eMJNs51Kkw1B7tY-zF2FeuZNUzpZ98AxLVcLtImTosm4q0KiZxsfqzawnQ83EdC9";
	
	public static JSONArray leerPlaylist() throws IOException {
		convertirPlaylist();
		JSONArray obj;
		try {
			JSONTokener tokener = new JSONTokener(
					new FileReader(System.getProperty("user.home") + "\\Desktop\\RecursosApp\\playlist2.json"));
			obj = new JSONArray(tokener);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"ERROR: El archivo 'playlist.json' no se ha encontrado.\nAsegúrese de que el archivo se encuentra en una carpeta llamada 'RecursosApp' en su escritorio.");
			System.exit(0);
			return null;
		} catch (JSONException e) {
			JOptionPane.showMessageDialog(null, "ERROR: El formato 'playlist.json' no es correcto.");
			System.exit(0);
			return null;
		}
		return obj;
	}

	public static int devolverLongitudPlaylist() throws IOException {
		JSONArray obj;
		try {
			JSONTokener tokener = new JSONTokener(
					new FileReader(System.getProperty("user.home") + "\\Desktop\\RecursosApp\\playlist2.json"));
			obj = new JSONArray(tokener);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"ERROR: El archivo 'playlist.json' no se ha encontrado.\nAsegúrese de que el archivo se encuentra en una carpeta llamada 'RecursosApp' en su escritorio.");
			System.exit(0);
			return 0;
		} catch (JSONException e) {
			JOptionPane.showMessageDialog(null, "ERROR: El formato 'playlist.json' no es correcto.");
			System.exit(0);
			return 0;
		}
		return obj.length();
	}

	public static Object[][] devolverCanciones(String tiempoActual) throws JSONException, IOException {
		JSONArray jsonPlayAux = leerPlaylist();
		Iterator<Object> it = jsonPlayAux.iterator();
		ArrayList<Object[]> lista = new ArrayList<Object[]>();
		int i = 1;
		while (it.hasNext()) {
			JSONObject jsonPlay = (JSONObject) it.next();
			String title = String.valueOf(jsonPlay.get("title"));
			String artist = String.valueOf(jsonPlay.get("artist"));
			String topGenre = String.valueOf(jsonPlay.get("top genre"));
			int year = jsonPlay.getInt("year");
			int bpm = jsonPlay.getInt("bpm");
			int nrgy = jsonPlay.getInt("nrgy");
			int dnce = jsonPlay.getInt("dnce");
			int dB = jsonPlay.getInt("dB");
			int val = jsonPlay.getInt("val");
			int dur = jsonPlay.getInt("dur");

			if (tiempoActual.equals("Rain") || tiempoActual.equals("Mist") || tiempoActual.equals("Drizzle")
					|| tiempoActual.equals("Smoke") || tiempoActual.equals("Fog") || tiempoActual.equals("Squall")
					|| tiempoActual.equals("Haze")) {
				if (sad(val, nrgy))
					lista.add(new Object[] { i++, title, artist, topGenre, String.valueOf(year), String.valueOf(dur)});
			}
			
			if (tiempoActual.equals("Clear")) {
				if (happy(val, nrgy))
					lista.add(new Object[] { i++, title, artist, topGenre, String.valueOf(year), String.valueOf(dur)});
			}
			
			if (tiempoActual.equals("Snow")) {
				if (danceable(dnce))
					lista.add(new Object[] { i++, title, artist, topGenre, String.valueOf(year), String.valueOf(dur)});
			}
			
			if (tiempoActual.equals("Thunderstorm") || tiempoActual.equals("Tornado")) {
				if (amped(nrgy))
					lista.add(new Object[] { i++, title, artist, topGenre, String.valueOf(year), String.valueOf(dur)});
			}

			if (tiempoActual.equals("Dust") || tiempoActual.equals("Sand") || tiempoActual.equals("Ash")) {
				if (anger(nrgy, dB, val))
					lista.add(new Object[] { i++, title, artist, topGenre, String.valueOf(year), String.valueOf(dur)});
			}

			if (tiempoActual.equals("Clouds")) {
				if (soft(nrgy, dB, val))
					lista.add(new Object[] { i++, title, artist, topGenre, String.valueOf(year), String.valueOf(dur)});
			}
		}
		Object[][] canciones = new Object[lista.size()][6];
		for (int j = 0; j < lista.size(); j++) {
			canciones[j] = lista.get(j);
		}
		return canciones;
	}

	public static JSONArray devolverCancionesArray(String tiempoActual) throws JSONException, IOException {
		JSONArray jsonPlayAux = leerPlaylist();
		JSONArray JSONCanciones = new JSONArray();
		Iterator<Object> it = jsonPlayAux.iterator();
		while (it.hasNext()) {
			JSONObject jsonPlay = (JSONObject) it.next();
			int nrgy = jsonPlay.getInt("nrgy");
			int dnce = jsonPlay.getInt("dnce");
			int dB = jsonPlay.getInt("dB");
			int val = jsonPlay.getInt("val");

			if (tiempoActual.equals("Rain") || tiempoActual.equals("Mist") || tiempoActual.equals("Drizzle")
					|| tiempoActual.equals("Smoke") || tiempoActual.equals("Fog") || tiempoActual.equals("Squall")
					|| tiempoActual.equals("Haze")) {
				if (sad(val, nrgy))
					JSONCanciones.put(jsonPlay);
			}
			
			if (tiempoActual.equals("Clear")) {
				if (happy(val, nrgy))
					JSONCanciones.put(jsonPlay);
			}
			
			if (tiempoActual.equals("Snow")) {
				if (danceable(dnce))
					JSONCanciones.put(jsonPlay);
			}
			
			if (tiempoActual.equals("Thunderstorm") || tiempoActual.equals("Tornado")) {
				if (amped(nrgy))
					JSONCanciones.put(jsonPlay);
			}

			if (tiempoActual.equals("Dust") || tiempoActual.equals("Sand") || tiempoActual.equals("Ash")) {
				if (anger(nrgy, dB, val))
					JSONCanciones.put(jsonPlay);
			}

			if (tiempoActual.equals("Clouds")) {
				if (soft(nrgy, dB, val))
					JSONCanciones.put(jsonPlay);
			}

		}
		return JSONCanciones;
	}

	public static JSONArray seleccionarCanciones(JSONArray JSONCanciones, JSONArray JCanciones) {
		JSONArray JCancionesSelect = new JSONArray();
		for (int i = 0; i < JSONCanciones.length(); i++) {
			for (int j = 0; j < JCanciones.length(); j++) {
				if (JSONCanciones.getJSONObject(i).getString("title")
						.equals(JCanciones.getJSONObject(j).getJSONObject("track").getString("name"))
						&& JSONCanciones.getJSONObject(i).getString("artist").equals(JCanciones.getJSONObject(j)
								.getJSONObject("track").getJSONArray("artists").getJSONObject(0).getString("name"))) {
					JCancionesSelect.put(JCanciones.getJSONObject(j));
					break;

				}
			}
		}
		return JCancionesSelect;
	}

	public static JSONObject leerAPITiempo(String ciudad, String APIkey) {
		String city = "?q=" + ciudad;
		String key = "&appid=" + APIkey;
		String URL = "http://api.openweathermap.org/data/2.5/weather" + city + key;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).GET().build();

		try {
			HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
			JSONTokener tokener = new JSONTokener(respuesta.body());
			return new JSONObject(tokener);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static boolean sad(int val, int energy) {
		if (val < 20 && energy < 20)
			return true;
		else
			return false;
	}

	public static boolean happy(int val, int energy) {
		if (val > 80 && energy > 80)
			return true;
		else
			return false;
	}

	public static boolean danceable(int dance) {
		if (dance > 80)
			return true;
		else
			return false;
	}

	public static boolean amped(int energy) {
		if (energy > 80)
			return true;
		else
			return false;
	}

	public static boolean anger(int energy, int db, int val) {
		if (energy > 80 && db >= -5 && val < 50)
			return true;
		else
			return false;
	}

	public static boolean soft(int energy, int db, int val) {
		if (energy < 50 && db < -5 && val < 20)
			return true;
		else
			return false;
	}

	public static void convertirPlaylist() throws IOException {
		// File archivo = new File(System.getProperty("user.dir") +
		// "\\src\\recursos\\playlist2.json");
		File archivo = new File(System.getProperty("user.home") + "\\Desktop\\RecursosApp\\playlist.json");
		FileReader fr = new FileReader(archivo);
		BufferedReader br = new BufferedReader(fr);
		String linea, linea2 = null;
		String texto = "[";
		while ((linea = br.readLine()) != null) {
			for (int i = 0; i < linea.length(); i++) {
				if (linea.charAt(i) != ' ') {
					linea2 = linea.substring(i);
					break;
				}
			}
			texto += linea2;
		}
		texto = texto.replace(",\"\": \"\"", "");
		texto = texto.substring(1);
		// Escritura
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			// fichero = new FileWriter(System.getProperty("user.dir") +
			// "\\src\\recursos\\playlist.json");
			fichero = new FileWriter(System.getProperty("user.home") + "\\Desktop\\RecursosApp\\playlist2.json");
			pw = new PrintWriter(fichero);
			pw.print(texto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static String crearPlaylist(String tiempoActual) throws IOException {
		// "https://api.spotify.com/v1/playlists/{playlist_id}/tracks"
		JSONObject jbody = new JSONObject();
		String auth = "Bearer " + token;
		jbody.put("name", "Playlist creada por MoodApp");
		jbody.put("description", "Playlist creada por MoodApp.");
		jbody.put("public", true);
		String URL = "https://api.spotify.com/v1/users/0cfvwsadvzuwq1dzbx3x32078/playlists";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-Type", "application/json")
				.header("Authorization", auth).header("Accept", "application/json")
				.POST(BodyPublishers.ofString(jbody.toString())).build();
		JSONObject JPlaylist = null;
		try {
			HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
			JSONTokener tokener = new JSONTokener(respuesta.body());
			JPlaylist = new JSONObject(tokener);

			JSONArray JCanciones = leerPlaylistSpotify();
			JSONArray JSONCanciones = devolverCancionesArray(tiempoActual);
			JSONArray JCancionesSelec = seleccionarCanciones(JSONCanciones, JCanciones);
			for (int i = 0; i < JCancionesSelec.length(); i++) {
				aniadirCancion(JPlaylist, i, JCancionesSelec.getJSONObject(i));
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JPlaylist.getString("id");
	}
	
	public static JSONArray leerPlaylistSpotify() throws IOException {
		JSONArray JCanciones = new JSONArray();
		for (int i = 0; i < devolverLongitudPlaylist(); i = i + 100) {
			JCanciones = concatenarJSON(JCanciones, leerPlaylistSpotifyAux(i));
		}
		return JCanciones;
	}

	public static JSONArray leerPlaylistSpotifyAux(int offset) throws IOException {
		String auth = "Bearer " + token;
		String URL = "https://api.spotify.com/v1/playlists/1jGwioCFxkoZQFlVcVkhaf/tracks?market=ES&fields=items(track(id%2Cname%2Cartists(name)))&limit=100&offset="
				+ String.valueOf(offset);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-Type", "application/json")
				.header("Authorization", auth).header("Accept", "application/json").GET().build();

		try {
			HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
			JSONTokener tokener = new JSONTokener(respuesta.body());
			JSONObject obj = new JSONObject(tokener);
			//System.out.println(obj.getJSONArray("items").length());
			return obj.getJSONArray("items");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static JSONArray concatenarJSON(JSONArray JCanciones, JSONArray JCancionesNuevas) {
		for (int i = 0; i < JCancionesNuevas.length(); i++) {
			JCanciones.put(JCancionesNuevas.getJSONObject(i)); // Para el id de la cancion:
																// JCanciones.getJSONObject(i).getJSONObject("track").getString("id");
		}
		return JCanciones;
	}

	public static void aniadirCancion(JSONObject JPlaylist, int posicion, JSONObject JCancion) throws IOException {
		JSONObject jbody = new JSONObject();
		String auth = "Bearer " + token;
		String pos = "?posicion=" + String.valueOf(posicion);
		String cancion = "&uris=spotify%3Atrack%3A" + JCancion.getJSONObject("track").getString("id");
		String URL = "https://api.spotify.com/v1/playlists/" + JPlaylist.getString("id") + "/tracks" + pos + cancion;

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-Type", "application/json")
				.header("Authorization", auth).header("Accept", "application/json")
				.POST(BodyPublishers.ofString(jbody.toString())).build();
		JSONObject obj;
		try {
			HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
			// JSONTokener tokener = new JSONTokener(respuesta.body());
			// obj =new JSONObject(tokener);
			//System.out.println(respuesta.body());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void getToken() {
		String URL = "https://accounts.spotify.com/api/token";
		String encodeBytes = Base64.getEncoder().encodeToString(("857b61e0c4a74df9983271c4dd8caa4a" + ":" + "c77b1f8a1a0c4bf4a6d339ae7ad3f3f1").getBytes());
		String auth = "Basic " + encodeBytes;
		JSONObject jbody = new JSONObject();
		jbody.put("grant_type", "client_credentials");
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Authorization", auth)
				.POST(BodyPublishers.ofString(jbody.toString())).build();
		
		try {
			HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
			//JSONTokener tokener = new JSONTokener(respuesta.body());
			//JSONObject obj = new JSONObject(tokener);			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			

		}
	}

}
