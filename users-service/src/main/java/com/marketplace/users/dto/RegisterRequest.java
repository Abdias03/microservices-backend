package com.marketplace.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
	
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Correo no válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    private String telefono;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @NotBlank(message = "El tipo de usuario es obligatorio")
    private String tipo;
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
    

}
