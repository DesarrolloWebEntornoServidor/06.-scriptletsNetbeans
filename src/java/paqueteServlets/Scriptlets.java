package paqueteServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Scriptlets")
public class Scriptlets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NL = "\n";
	private LinkedHashMap<String,String> arrayValoresYEtiquetas = new LinkedHashMap<String,String>(); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		arrayValoresYEtiquetas.put("ES", "España");
		arrayValoresYEtiquetas.put("FR", "Francia");
		arrayValoresYEtiquetas.put("IT", "Italia");
		arrayValoresYEtiquetas.put("PT", "Portugal");		

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println ("<h1>Scriptlets</h1>");
                out.println("Genera Radio Button: <br />");
                out.println(generaBotonesRadio("pais", arrayValoresYEtiquetas, "IT"));
		out.println("<br /> <br /> Genera CheckBox Simple: <br />");
		out.println(generaCajaChequeo("pais", "España", "ES", true));
		out.println("<br /> <br />Genera CheckBox Múltiple: <br />");
		out.println(generaArrayCajasChequeo("pais[]", arrayValoresYEtiquetas, new String[]{"ES", "IT"}));
		out.println("<br /> <br /> Genera Select Simple: <br />");
		out.println(generaSelectSimple("pais", arrayValoresYEtiquetas, "FR"));
		out.println("<br /> <br /> Genera Select Múltiple: <br />");
		out.println(generaSelectMultiple("pais[]", arrayValoresYEtiquetas, new String[]{"FR", "IT"}, 4));
	}
		
	/*
	  nombreControl: valor del atributo name de todos los input
	  arrayValoresYEtiquetas: array asociativo de pares (valor, etiqueta) 
                                  para el atributo value de cada radio y para el label de cada radio
	  valorSeleccionado: valor del atributo value del elemento seleccionado por el cliente, 
                             si es vacío no se selecciona ninguno	
	*/
	private String generaBotonesRadio(String nombreControl, Map<String,String> arrayValoresYEtiquetas, String valorSeleccionado) {
	  String salida = "";
	  Iterator<String> iteradorConjuntoClaves = arrayValoresYEtiquetas.keySet().iterator();
	  while (iteradorConjuntoClaves.hasNext()){
		  String clave = iteradorConjuntoClaves.next();
		  String valor = arrayValoresYEtiquetas.get(clave);
		  if (valorSeleccionado.equals(clave)) {
			  salida += "<label>" + valor + "</label><input type=\"radio\" name=\"" + nombreControl + "\" value=\"" + clave + "\" checked=\"checked\" />" + "\n";
		  } else {
			  salida += "<label>" + valor + "</label><input type=\"radio\" name=\"" + nombreControl + "\" value=\"" + clave + "\" />" + "\n";
		  }
	  }
	  return salida;
	}	

	/*
          nombreControl: valor del atributo name del checkbox
          valorControl:  contenido del label
          etiquetaControl: valor del atributo value
          seleccionado: booleano que indica si el checkbox ha sido seleccionado       
	*/
	
        private String generaCajaChequeo(String nombreControl, String valorControl, String etiquetaControl, Boolean seleccionado) {
            String salida="";
            if (seleccionado) {
                salida= "<label>" + valorControl + "</label><input type=\"checkbox\" name=\"" + nombreControl +
                    "\" value=\"" + etiquetaControl + "\" checked=\"checked\" />" + "\n";
            }
            else {
                salida= "<label>" + valorControl + "</label><input type=\"checkbox\" name=\"" + nombreControl +
                    "\" value=\"" + etiquetaControl + "\" />\n";
            }
            return salida;
	}

	/*
	  arrayValoresYEtiquetas: array asociativo de pares (valor, etiqueta) para el atributo value
	    de cada checkbox y para el nombre y label de cada checkbox
	  valoresSeleccionados: un array de valores que son los value de los ckeckbox seleccionados
	*/
	private String generaArrayCajasChequeo(String nombreControl, Map<String,String> arrayValoresYEtiquetas, String[] valoresSeleccionados) {
		String salida = "";
		int numerovaloresSeleccionados = valoresSeleccionados.length;  // cuántos valores seleccionados se han recibido
		if (numerovaloresSeleccionados > 0) {  // hay algún valor seleccionado
			int contadorValoresSeleccionados = 0;  // cuántos valores seleccionados ya se han recorrido
			Iterator<String> iteradorConjuntoClaves = arrayValoresYEtiquetas.keySet().iterator();
			while (iteradorConjuntoClaves.hasNext()) {
				String clave = iteradorConjuntoClaves.next();
				String valor = arrayValoresYEtiquetas.get(clave);
				if ( (contadorValoresSeleccionados < numerovaloresSeleccionados) &&
		             (valoresSeleccionados[contadorValoresSeleccionados].equals(clave)) ) {
					salida += "<label>" + valor + "</label><input type=\"checkbox\" name=\"" + nombreControl + "\" value=\"" + clave + "\" checked=\"checked\" />" + "\n";
					contadorValoresSeleccionados++;
				} else {
					salida += "<label>" + valor + "</label><input type=\"checkbox\" name=\"" + nombreControl + "\" value=\"" + clave + "\" />" + "\n";
				}  
			}
		} else {
			Iterator<String> iteradorConjuntoClaves = arrayValoresYEtiquetas.keySet().iterator();
			while (iteradorConjuntoClaves.hasNext()) {
				String clave = iteradorConjuntoClaves.next();
				String valor = arrayValoresYEtiquetas.get(clave);
				salida += "<label>" + valor + "</label><input type=\"checkbox\" name=\"" + nombreControl + "\" value=\"" + clave + "\" />" + "\n";
			}    
		}  
		return salida;
	}


	/*
	  nombreControl: valor del atributo name del select
	  arrayValoresYTextos: array asociativo de pares (valor, texto) para el atributo value de cada option
	    y para el texto de cada otion
	  valorSeleccionado: value del option seleccionado
	*/
	private String generaSelectSimple(String nombreControl, Map<String,String> arrayValoresYTextos, String valorSeleccionado) {
		String salida = "";
	    salida += "<select name=\"" + nombreControl + "\">" + "\n";
		Iterator<String> iteradorConjuntoClaves = arrayValoresYEtiquetas.keySet().iterator();
		while (iteradorConjuntoClaves.hasNext()) {
			String clave = iteradorConjuntoClaves.next();
			String valor = arrayValoresYEtiquetas.get(clave);
			if (valorSeleccionado.equals(clave)) {
				salida += "  <option value=\"" + clave + "\" selected=\"selected\">" + valor + "</option>" + "\n";  
			  } else {
				salida += "  <option value=\"" + clave + "\">" + valor + "</option>" + "\n";
			  }
		  }
		  salida += "</select>" + "\n";	
		  return salida;	
	}

	
	/*
	  nombreControl: valor del atributo name del select
	  arrayValoresYTextos: array asociativo de pares (valor, texto) para el atributo value de cada option
	    y para el texto de cada otion
	  valoresSeleccionados: un array de valores, los value de los option seleccionados
	  opcionesVisibles: número de opciones visibles del select múltiple
	*/
	private String generaSelectMultiple(String nombreControl, Map<String,String> arrayValoresYTextos, String[] valoresSeleccionados, int opcionesVisibles) {
		String salida = "";	
	    salida += "<select name=\"" + nombreControl + "\" multiple=\"multiple\" size=\"" + opcionesVisibles + "\">" + "\n";
	    int contadorValoresSeleccionados = 0;  // cuántos valores seleccionados ya se han recorrido
		int numerovaloresSeleccionados = valoresSeleccionados.length;  // cuántos valores seleccionados se han recibido
		Iterator<String> iteradorConjuntoClaves = arrayValoresYTextos.keySet().iterator();
		while (iteradorConjuntoClaves.hasNext()) {
			String clave = iteradorConjuntoClaves.next();
			String valor = arrayValoresYTextos.get(clave);
			if ( (contadorValoresSeleccionados < numerovaloresSeleccionados) &&
	             (valoresSeleccionados[contadorValoresSeleccionados].equals(clave)) ) {
				salida += "  <option value=\"" + clave + "\" selected=\"selected\">" + valor + "</option>" + "\n";
				contadorValoresSeleccionados++;
			} else {
				salida += "  <option value=\"" + clave + "\">" + valor + "</option>" + "\n";
			}
		}
		salida += "</select>" + "\n";	
		return salida;
	}
	
	
/*	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
*/
}
