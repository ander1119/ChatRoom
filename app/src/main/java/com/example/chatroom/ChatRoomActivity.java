package com.example.chatroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Date;

public class ChatRoomActivity extends AppCompatActivity {
    private FloatingActionButton send;
    private Button logout;
    private String uid;
    private TextInputEditText message_input;

    private FirebaseRecyclerAdapter<Message, MessageHolder> adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        message_input = (TextInputEditText) findViewById(R.id.message_input);
        send = (FloatingActionButton) findViewById(R.id.send_msg);
        logout = (Button) findViewById((R.id.logout));

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);

        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
            @Override//收到新訊息時自動往下捲
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (adapter != null)
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        send.setImageResource(R.drawable.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = message_input.getEditableText().toString();
                String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                System.out.println(userName);
                long time = new Date().getTime();
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new Message(userName, message, time, uid));
                message_input.setText("");
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
                builder.setCancelable(false)
                        .setTitle("LOGOUT")
                        .setMessage("Are you sure you want to log out ?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AuthUI.getInstance().signOut(ChatRoomActivity.this)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(ChatRoomActivity.this, "Logout already !", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Intent intent = new Intent();
                                                intent.setClass(ChatRoomActivity.this, StartUpActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                builder.show();
            }
        });
        displayMsg();
    }

    private void displayMsg(){
        try {
            adapter = new FirebaseRecyclerAdapter<Message, MessageHolder>(Message.class, R.layout.message, MessageHolder.class, FirebaseDatabase.getInstance().getReference().limitToLast(10)) {
                public MessageHolder onCreateMessageHolder(ViewGroup parent, int ViewType){
                    View v = LayoutInflater.from(ChatRoomActivity.this).inflate(R.layout.message, parent, false);
                    MessageHolder holder = new MessageHolder(v);
                    return holder;
                }

                @Override
                protected void populateViewHolder(MessageHolder messageHolder, Message message, int i) {
                    messageHolder.setMessageValue(message, uid);
                }
            };

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}