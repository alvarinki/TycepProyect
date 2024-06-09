package com.example.proyecto.services;

import com.example.proyecto.modelFB.ChatFB;
import com.example.proyecto.modelFB.UsuarioFB;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.database.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.grpc.internal.DnsNameResolver;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirebaseService {

    private static FirebaseApp firebaseApp;

    @PostConstruct
    public void initialize() throws IOException {
        if (firebaseApp == null) {
//            FileInputStream serviceAccount = new FileInputStream("pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json");
            try (InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json")) {
                if (serviceAccount == null) {
                    throw new IOException("File not found: pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json");
                }
            //Casa FileInputStream serviceAccount = new FileInputStream("C:\\Users\\alvar\\OneDrive\\Escritorio\\pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://pruebatycep-default-rtdb.europe-west1.firebasedatabase.app/")
                    .build();

            //firebaseApp = FirebaseApp.initializeApp(options);
            List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
            if (firebaseApps.isEmpty()) {
                firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                firebaseApp = firebaseApps.get(0); // Obtener la instancia existente
            }
        }
        }
        //CASA
        //  "pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json"
//Casa "C:\Users\alvar\OneDrive\Escritorio\pruebatycep-firebase-adminsdk-ud9iq-54eb686358.json"
//Curro "pruebatycep-firebase-adminsdk-ud9iq-5f8a2e4ce7.json"
    }

    public void comprobarYcrearChat(ChatFB chatFB) {
        Query query = FirebaseDatabase.getInstance().getReference("Chat")
                .orderByChild("nombreChat")
                .startAt(chatFB.getNombreChat())
                .endAt(chatFB.getNombreChat() + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ChatFB> matchingChats = new ArrayList<>();
                boolean chatExists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatFB existingChat = snapshot.getValue(ChatFB.class);
                    if (existingChat != null && existingChat.getNombreChat().contains(chatFB.getNombreChat())) {
                        //matchingChats.add(existingChat);
                        chatExists=true;
                    }
                }

//                for (ChatFB existingChat : matchingChats) {
//                    if (existingChat.getUsuarios().equals(chatFB.getUsuarios())) {
//                        chatExists = true;
//                        break;
//                    }
//                }

                if (!chatExists) {
                    guardarChat(chatFB);
                } else {
                    System.out.println("Chat with the same users and a name containing the search string already exists.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Database error: " + databaseError.getMessage());
            }
        });
    }

    public void guardarChat(ChatFB chatFB) {
        // Obtener una referencia a la ubicación en la base de datos Firebase
        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chat");
        // Generar una clave única para el nuevo chat
        String chatId = chatsRef.push().getKey();

        if (chatId != null) {
            chatFB.setId(chatId);
            List<String> usernames = chatFB.getUsuarios().keySet().stream().toList();
//            Map<String, Boolean> keySetUsuarios= obtenerIdsDeUsernames(usernames);
//            chatFB.setUsuarios(keySetUsuarios);
            Map<String, Object> chatValues = chatFB.toMap();
            chatsRef.child(chatId).setValue(chatValues, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error == null) {
                        System.out.println("Chat guardado exitosamente en la ubicación: " + ref.getKey());
                        actualizarUsuariosConChat(usernames, chatFB.getId());
                    } else {
                        System.err.println("Error al guardar el chat: " + error.getMessage());
                    }
                }
            });
        }
    }

    private void actualizarUsuariosConChat(List<String> usernames, String chatId) {
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Iterar solo sobre los nombres de usuario incluidos en el chatFB
        for (String username : usernames) {
            // Buscar al usuario por nombre de usuario
            System.out.println("usuario a buscar: "+username);
            Query query = usuariosRef.orderByChild("username").equalTo(username);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        System.out.println("User id de usuarios buscados: "+userId);
                        usuariosRef.child(userId).child("chats").child(chatId).setValue(true, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError error, DatabaseReference ref) {
                                if (error == null) {
                                    System.out.println("Chat añadido al usuario: " + username);
                                } else {
                                    System.err.println("Error al actualizar el usuario: " + error.getMessage());
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Database error: " + databaseError.getMessage());
                }
            });
        }
    }

//    public Map<String, Boolean> obtenerIdsDeUsernames(List<String> usernames) {
//        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("Usuarios");
//        Map<String, Boolean> usuarios = new HashMap<>();
//        final boolean[] isCompleted = {false};
//        final int[] usuariosProcesados = {0};
//        int totalUsuarios = usernames.size();
//
//        System.out.println("Usernames que llegan a obtenerids: " + usernames);
//
//        for (String username : usernames) {
//            System.out.println("Procesando username: " + username);
//            Query query = usuariosRef.orderByChild("username").equalTo(username);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    System.out.println("onDataChange called for username: " + username);
//                    if (dataSnapshot.exists()) {
//                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                            String userId = userSnapshot.getKey();
//                            usuarios.put(userId, true);
//                            System.out.println("id de usuario añadido en obtenerids: " + userId);
//                        }
//                    } else {
//                        System.out.println("No user found for username: " + username);
//                    }
//
//                    // Incrementar el contador de usuarios procesados
//                    usuariosProcesados[0]++;
//                    if (usuariosProcesados[0] == totalUsuarios) {
//                        // Todos los usuarios han sido procesados
//                        isCompleted[0] = true;
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    System.err.println("Error en la búsqueda de usuarios para guardar chat: " + databaseError.getMessage());
//                    isCompleted[0] = true;
//                }
//            });
//        }
//
//        // Esperar hasta que se completen todas las operaciones asíncronas
//        while (!isCompleted[0]) {
//            try {
//                Thread.sleep(100); // Esperar 100 milisegundos antes de verificar de nuevo
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.err.println("Error en Thread.sleep(): " + e.getMessage());
//                isCompleted[0] = true;
//            }
//        }
//
//        System.out.println("Usuarios obtenidos: " + usuarios);
//        return usuarios;
//    }

    public void guardarUsuarios(List<UsuarioFB> usuarios) {
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("Usuarios");
        for(UsuarioFB usuarioFB : usuarios) {
        // Generar un ID único para el usuario
        String userId = usuariosRef.push().getKey();
        usuarioFB.setId(userId);
        // Guardar el usuario en la base de datos
        usuariosRef.child(userId).setValue(usuarioFB.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    // Éxito al guardar el usuario
                    System.out.println("Usuario guardado exitosamente en la ubicación: " + ref.getKey());
                } else {
                    // Manejar el error al guardar el usuario
                    System.err.println("Error al guardar el usuario: " + error.getMessage());
                }
            }
        });}
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

    public void guardarUsuario(UsuarioFB usuarioFB) {
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Generar un ID único para el usuario
        String userId = usuariosRef.push().getKey();

        // Guardar el usuario en la base de datos
        usuariosRef.child(userId).setValue(usuarioFB.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    // Éxito al guardar el usuario
                    System.out.println("Usuario guardado exitosamente en la ubicación: " + ref.getKey());
                } else {
                    // Manejar el error al guardar el usuario
                    System.err.println("Error al guardar el usuario: " + error.getMessage());
                }
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

