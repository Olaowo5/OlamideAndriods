package algonquin.cst2335.owol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.owol.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.owol.databinding.SentMessageBinding;
import algonquin.cst2335.owol.databinding.ReceiveMessageBinding;


public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ArrayList<ChatMessage> messages = new ArrayList<>();

    ChatRoomViewModel chatModel;

    private RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_chat_room);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
        }

        binding.sendbtn.setOnClickListener(click -> {



            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            String CDT = currentDateandTime;

            ChatMessage Cm = new ChatMessage(binding.editTextO.getText().toString(),CDT,true);

            messages.add(Cm);

            myAdapter.notifyItemInserted(messages.size()-1);
            //clear previous text
            binding.editTextO.setText("");


        });

        binding.recbtn.setOnClickListener(click -> {



            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            String CDT = currentDateandTime;

            ChatMessage Cm = new ChatMessage(binding.editTextO.getText().toString(),CDT,false);

            messages.add(Cm);

            myAdapter.notifyItemInserted(messages.size()-1);
            //clear previous text
            binding.editTextO.setText("");


        });

        binding.recyleOla.setLayoutManager(new LinearLayoutManager(this));
        binding.recyleOla.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //return null;


                if(viewType == 0)
                {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(),parent,
                            false);
                    return new MyRowHolder(binding.getRoot());
                }
                else{

                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(),
                            parent, false);
                    return new MyRowHolder(binding.getRoot());
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {


                String TheMessage = messages.get(position).Getmessage();
                String TheTime = messages.get(position).GetTime();
                holder.MessageText.setText("");
                holder.TimeText.setText("");





                holder.MessageText.setText(TheMessage);
                holder.TimeText.setText(TheTime);
            }

            @Override
            public int getItemCount() {
                //return 0;
                return messages.size();
            }

           public int getItemViewType(int position){
                return position % 2;
            }
        });
    }


    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView MessageText;
        TextView TimeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            MessageText = itemView.findViewById(R.id.Message);
            TimeText = itemView.findViewById(R.id.Time);
        }
    }
}

