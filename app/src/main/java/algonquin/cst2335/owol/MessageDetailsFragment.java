package algonquin.cst2335.owol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.owol.databinding.DetailsLayoutBinding;





public class MessageDetailsFragment extends Fragment {

    ChatMessage selected;
    public MessageDetailsFragment (ChatMessage cm)
    {
        selected =cm;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
      /*return */ super.onCreateView(inflater, group, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

       binding.Dmessage.setText(selected.message);
       binding.DTime.setText(selected.timeSent);
       String IsSent = "Is Sent";
       if(!selected.isSentButton)
           IsSent = "Is Receive";

       binding.Dsendmessage.setText(IsSent);
       binding.Ddatabase.setText("Id = " + selected.Id);

        return binding.getRoot();
    }


}
