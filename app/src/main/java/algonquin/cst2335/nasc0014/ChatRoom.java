package algonquin.cst2335.nasc0014;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import algonquin.cst2335.nasc0014.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.nasc0014.databinding.ReceiveMessageBinding;
import algonquin.cst2335.nasc0014.databinding.SentMessageBinding;
import algonquin.cst2335.nasc0014.viewmodel.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;

    private RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        binding.buttonSend.setOnClickListener(click ->{
            String messageText = binding.textInput.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a", Locale.getDefault());
                String timeSent = sdf.format(new Date());
                ChatMessage newMessage = new ChatMessage(messageText, timeSent, true);
                messages.add(newMessage);
                myAdapter.notifyItemInserted(messages.size() - 1);
                binding.textInput.setText("");
            });

        binding.buttonReceive.setOnClickListener(click ->{
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
                return new MyRowHolder(binding.getRoot());}
                else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());}
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
            public int getItemViewType(int position){
                ChatMessage message = messages.get(position);
                if (message.isSentButton(true)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });



        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
        }

    }

    class MyRowHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }


    public class ChatMessage {

        private final String message;
        private final String timeSent;
        private final boolean isSentButton;

        public ChatMessage(String message, String timeSent, boolean isSentButton) {
            this.message = message;
            this.timeSent = timeSent;
            this.isSentButton = isSentButton;
        }

        public String getMessage() {
            return message;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public boolean isSentButton(boolean b) {
            return isSentButton;
        }
    }



    }
