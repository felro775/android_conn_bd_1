package com.decodexs.conexion01;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        connectionClass = new ConnectionClass();
        conectar();
    }

    public void btnClick(View view) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                con = connectionClass.CONN();
                String consulta = "SELECT * FROM clubes WHERE id_club = 3";
                PreparedStatement stmt = con.prepareStatement(consulta);
                ResultSet rs = stmt.executeQuery();
                StringBuilder bStr = new StringBuilder("Lista de Equipos\n");
                while (rs.next()){
                    bStr.append(rs.getString("nombre_club")).append("\n");
                }
                name = bStr.toString();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(() ->{
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                TextView txtLista = findViewById(R.id.textView1);
                txtLista.setText(name);
            });
        });
    }

    public void conectar(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
                try{
                    con = connectionClass.CONN();

                    if (con == null){
                        str = "Error en la Coneccion con Servidor MySQL";
                    }else{
                        str = "CONECTADO con Servidor MySQL";
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                runOnUiThread(() ->{
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                });
        });
    }
}
