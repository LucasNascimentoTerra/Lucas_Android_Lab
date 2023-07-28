package algonquin.cst2335.nasc0014;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.nasc0014.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessage selectedMessage;
    public MessageDetailsFragment(ChatMessage m){

        selectedMessage = m;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding =  DetailsLayoutBinding.inflate(inflater);


        binding.messageText.setText(selectedMessage.messageText);
        binding.timeText.setText(selectedMessage.timeSent);
        binding.databaseText.setText("ID: " + selectedMessage.id);



        return binding.getRoot();
    }




}

