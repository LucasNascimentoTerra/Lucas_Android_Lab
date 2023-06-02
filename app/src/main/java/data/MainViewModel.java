package data;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public MutableLiveData<String> editString = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelected = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean value) {
        isSelected.setValue(value);
    }
}
