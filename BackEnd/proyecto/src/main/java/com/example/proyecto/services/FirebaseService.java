package com.example.proyecto.services;

import com.example.proyecto.model.Chat;
import com.example.proyecto.model.Usuario;
import com.example.proyecto.modelFB.ChatFB;
import com.example.proyecto.modelFB.UsuarioFB;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.database.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {

    private static FirebaseApp firebaseApp;

    @PostConstruct
    public void initialize() throws IOException {
        if (firebaseApp == null) {
            FileInputStream serviceAccount = new FileInputStream("C:/Users/alvar/OneDrive/Escritorio/pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://pruebatycep-default-rtdb.europe-west1.firebasedatabase.app/")
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }

    }
    //"C:/Users/alvar/OneDrive/Escritorio/pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json"
    public void guardarChat(ChatFB chatFB) {
        // Obtener una referencia a la ubicación en la base de datos Firebase
        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");
        System.out.println(chatsRef.getRef());
        // Guardar el chat en la base de datos
        chatsRef.push().setValue(chatFB.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    // Éxito al guardar el usuario
                    System.out.println("Chat guardado exitosamente en la ubicación: " + ref.getKey());
                } else {
                    // Manejar el error al guardar el usuario
                    System.err.println("Error al guardar el chat: " + error.getMessage());
                }
            }
        });
    }

    public void obtenerUsuariosDeChat(String chatId) {
        String safeIdChat = chatId.replaceAll("[.$#\\[\\]/]", "_");
        DatabaseReference chatUsuariosRef = FirebaseDatabase.getInstance().getReference("Chats").child(safeIdChat).child("usuarios");
        chatUsuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    System.out.println("Usuario en el chat: " + userId);
                    //Aquí podrías cargar más datos del usuario si es necesario
                    //obtenerDatosUsuario(userId);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error al obtener usuarios del chat: " + error.getMessage());
            }
        });
    }

}
//    private void obtenerDatosUsuario(String userId) {
//        DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(userId);
//        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    UsuarioFB usuario = snapshot.getValue(UsuarioFB.class);
//                    System.out.println("Datos del usuario: " + usuario.getUsername());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                System.err.println("Error al obtener datos del usuario: " + error.getMessage());
//            }
//
//        });
//
//    }

//    @PostConstruct
//    public void initialize() throws IOException {
//        if (FirebaseApp.getApps().isEmpty()) {
//            FileInputStream serviceAccount =
//                    new FileInputStream("C:/Users/acf/Desktop/Formacion/Proyecto/pruebatycep-firebase-adminsdk-ud9iq-5f8a2e4ce7.json");
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//        }

//    }
//    public FirebaseService() {
//        try {
//            // Inicializa Firebase con las opciones de configuración
//            FileInputStream serviceAccount = new FileInputStream("C:/Users/acf/Desktop/Formacion/Proyecto/pruebatycep-firebase-adminsdk-ud9iq-5f8a2e4ce7.json");
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://pruebatycep-default-rtdb.europe-west1.firebasedatabase.app")
//                    .build();
//            FirebaseApp.initializeApp(options);
//
//            // Obtiene una instancia de la base de datos de Firebase
//            this.database = FirebaseDatabase.getInstance();
//        } catch (IOException e) {
//            // Manejar la excepción de IO
//            e.printStackTrace();
//            throw new RuntimeException("Error al inicializar Firebase", e);
//        }

//    }


//"C:\Users\acf\Desktop\Formacion\Proyecto\pruebatycep-firebase-adminsdk-ud9iq-5f8a2e4ce7.json"
