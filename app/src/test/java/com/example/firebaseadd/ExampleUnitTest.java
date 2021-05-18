package com.example.firebaseadd;

import androidx.annotation.NonNull;
import com.example.firebaseadd.model.User;
import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private DatabaseReference reference;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testAddFriend(){
        List<User> friendUsers = new ArrayList<>();
        FireBaseConnection fireBaseConnection=new FireBaseConnection();
        List<String> userInFriends=new ArrayList<>();
        reference = fireBaseConnection.getFriends().child("MK6istiETRXpN2BzMKXi4x2VpeA3");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInFriends.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    userInFriends.add(it.getKey());

                }
                reference = fireBaseConnection.getMyUsers(); //Observer - observable (try to move addValueEventListener to FireBaseConnection) friendUsers -> observer
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friendUsers.clear();
                        for (DataSnapshot it : snapshot.getChildren()) {
                            if (userInFriends.contains(it.getKey())) {
                                User user = new User(it.getKey(), it.getValue(User.class).getUsername(), null); //parsing -> public method parse snapshot -> FireBaseConnection -> test(!)
                                friendUsers.add(user);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        System.out.println("The read failed: " + error.getCode());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
        List<User> friendUsersForTest = new ArrayList<>();
        friendUsersForTest.add(new User("FCcz1SfDUrgXPgwy2pWOk4742pH2","second",null));
        friendUsersForTest.add(new User("KbWGPEWO3XN3Y4XUOknWFOjq5dS2","teseh",null));
        assertEquals(true,friendUsers.equals(friendUsersForTest));
    }



    @Test
    public void testAddIgnore(){

    }
    @Test
    public void testChatUser(){

    }
    @Test
    public void testFieldsProfile(){

    }
    @Test
    public void testSendMessage(){

    }
    @Test
    public void testRegistration(){

    }

}

/*class FireBaseConnector {
    private List<Chat> results = new ArrayList<>(); //+getter
    public void loadSortedChats() {
        it.setCallBack(new … () {
            results.addAll(data);
        });
    }
}*/

    /*void test{
        FireBaseConnector fireBaseConnector=new FireBaseConnector();
        fireBaseConnector.loadSortedChats();
        boolean isLoaded=false;
        int i=0;
        while(!isLoaded&&i<10){   // Контролируемое количество записей?
        if(fireBaseConnector.getResults().count()==controlNumber){
        isLoaded=true;
        }
        Thread.sleep(1000);
        i++;
        }
        if(!isLoaded){
        fail();
        }
        }*/