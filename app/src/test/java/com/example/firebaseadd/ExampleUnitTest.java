package com.example.firebaseadd;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public void test() {
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