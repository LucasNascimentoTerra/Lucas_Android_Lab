package algonquin.cst2335.nasc0014.viewmodel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

import algonquin.cst2335.nasc0014.ChatRoom;


public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatRoom.ChatMessage>> messages = new MutableLiveData< >();
}