package algonquin.cst2335.owol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.owol.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.owol.databinding.SentMessageBinding;
import algonquin.cst2335.owol.databinding.ReceiveMessageBinding;


public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;


    ArrayList<ChatMessage> messages = new ArrayList<>();

    ChatRoomViewModel chatModel;
    ChatMessageDAO mDAO;

    private RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_chat_room);
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if(messages == null)
        {
           chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recyleOla.setAdapter( myAdapter )); //You can then load the RecyclerView
            });

        }

        binding.sendbtn.setOnClickListener(click -> {


            if(binding.editTextO.getText() == null || binding.editTextO.getText().toString().trim().isEmpty()) {
                // textView is empty or contains only blank space
                //stop here
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            String CDT = currentDateandTime;

            ChatMessage Cm = new ChatMessage(binding.editTextO.getText().toString(),CDT,true);
            //ChatMessage Cm = new ChatMessage("Jon sending",CDT,true);

            //clear previous text
            binding.editTextO.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                Cm.Id = (int)mDAO.insertMessage(Cm);
            });

            messages.add(Cm);

            myAdapter.notifyItemInserted(messages.size()-1);


        });

        binding.recbtn.setOnClickListener(click -> {


            if(binding.editTextO.getText() == null || binding.editTextO.getText().toString().trim().isEmpty()) {
                // textView is empty or contains only blank space
                //stop here
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            String CDT = currentDateandTime;

            ChatMessage Cm = new ChatMessage(binding.editTextO.getText().toString(),CDT,false);


            //clear previous text
            binding.editTextO.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                Cm.Id = (int)mDAO.insertMessage(Cm);
            });

            messages.add(Cm);

            myAdapter.notifyItemInserted(messages.size()-1);


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

               ChatMessage TheMessage = messages.get(position);

               if(TheMessage.IsSent())
                return 0;
               else
                   return 1;
            }
        });


        chatModel.selectedMessage.observe(this, (newMessageValue) -> {

            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue );
            //newValue is the newly set ChatMessage
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, chatFragment)
                    .addToBackStack("")
                    .commit();

                    getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {
                            if(chatFragment.isVisible())
                            {
                                chatFragment.getView().setBackgroundColor(getResources().getColor(R.color.my_secondarydark));
                            }
                        }
                    });





        });

    }


    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView MessageText;
        TextView TimeText;


        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            MessageText = itemView.findViewById(R.id.Message);
            TimeText = itemView.findViewById(R.id.Time);

            itemView.setOnClickListener(clk ->{
                /*
                //get postion of the row that is picked
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

                builder.setMessage("Do you want to delete the message: " + MessageText.getText())
                .setTitle("Question")

                .setPositiveButton("Yes",(dialog,cl)->{

                    ChatMessage cm = messages.get(position);

                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(()->{
                        mDAO.deleteMessage(cm);
                    });


                    messages.remove(position);
                    myAdapter.notifyItemRemoved(position);


                    Snackbar.make(MessageText, "You deleted message #" + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Undo",clo->{
                                //undo call in action
                                messages.add(position,cm);
                                myAdapter.notifyItemInserted(position);


                                thread.execute(()->{
                                    mDAO.insertMessage(cm);
                                });

                            })
                            .show();


                })
                .setNegativeButton("No",(dialog, cl)->{

                })

                .create().show();


                 */

                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);
            });

        }
    }
}

