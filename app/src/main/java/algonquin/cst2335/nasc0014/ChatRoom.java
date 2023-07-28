package algonquin.cst2335.nasc0014;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.nasc0014.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.nasc0014.databinding.ReceiveMessageBinding;
import algonquin.cst2335.nasc0014.databinding.SentMessageBinding;
import algonquin.cst2335.nasc0014.viewmodel.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<algonquin.cst2335.nasc0014.ChatMessage> messages;
    ChatRoomViewModel chatModel;

    private RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        chatModel.selectedMessage.observe(this, newMessageValue -> {
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            Fragment previousFragment = fMgr.findFragmentById(R.id.fragmentLocation);
            if (previousFragment != null){
                tx.remove(previousFragment);
            }
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
            tx.add(R.id.fragmentLocation, chatFragment);
            tx.addToBackStack(null);
            tx.commit();
        });
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }



        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        ChatMessageDAO mDAO = db.cmDAO();

        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<ChatMessage>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {

                messages.addAll(mDAO.getAllMessages()); //Once you get the data from database

                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter)); //You can then load the RecyclerView
            });
        }

/**
 * Week 6
 */
        binding.buttonSend.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a", Locale.getDefault());
            String timeSent = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(messageText, timeSent, true);
            messages.add(newMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
        });

        binding.buttonReceive.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a", Locale.getDefault());
            String timeSent = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(messageText, timeSent, false);
            messages.add(newMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));


        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                holder.messageText.setText("");
                holder.timeText.setText(obj.getTimeSent());
                holder.messageText.setText(obj.getMessage());


            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage message = messages.get(position);
                if (message.isSentButton(true)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });



    }

    class MyRowHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{

                int position = getAdapterPosition();


/*
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                .setTitle("Question:")
                        .setNegativeButton("No", (dialog, cl) ->{ })
                        .setPositiveButton("Yes", (dialog, cl) -> {

                            ChatMessage removedMessage = messages.get(position);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);

                            Snackbar.make(messageText, "You deleted the message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clk -> {
                                        messages.add(position, removedMessage);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();

                        }).create().show();
*/
                ChatMessage selectedMessage = messages.get(position);

                chatModel.selectedMessage.postValue(selectedMessage);
            });



            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

        }
    }






