package com.example.tic_tac

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    // database instance
    private var database= FirebaseDatabase.getInstance()
    private var myref= database.reference

    var myemail:String?=null // my email
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
     var b = intent.extras
        myemail= b!!.getString("email")

        // to accept or decline request
        incomingcalls()


    }

    protected fun buttclick(view: View) {
   val  buselected = view as Button
        buselected.text="Let's start the GAME!!"
        buselected.setBackgroundColor(Color.GREEN)
        var cellid=0
        Toast.makeText(this ,"Congrats !!! whewwww ..Player : $cellid won the game" , Toast.LENGTH_LONG).show()
        when(buselected.id){
            R.id.b1->cellid=1
            R.id.b2->cellid=2
            R.id.b3->cellid=3
            R.id.b4->cellid=4
            R.id.b5->cellid=5
            R.id.b6->cellid=6
            R.id.b7->cellid=7
            R.id.b8->cellid=8
            R.id.b9->cellid=9
           // R.id.b10->cellid=10

        }
        //playgame(cellid , buselected)
    }

    fun butclick(view: View) {
        val  buselected = view as Button
        var cellid=0
       // Toast.makeText(this ,"Congrats !!! whewwww ..Player : $cellid won the game" , Toast.LENGTH_LONG).show()
        when(buselected.id){
            R.id.b1->cellid=1
            R.id.b2->cellid=2
            R.id.b3->cellid=3
            R.id.b4->cellid=4
            R.id.b5->cellid=5
            R.id.b6->cellid=6
            R.id.b7->cellid=7
            R.id.b8->cellid=8
            R.id.b9->cellid=9
           // R.id.b10->cellid=10

        }
       // playgame(cellid , buselected)
        myref.child("PlayerOnline").child((sessionID!!)).child(cellid.toString()).setValue(myemail)

    }
     var p1=ArrayList<Int>()
     var p2=ArrayList<Int>()
     var active=1
    fun restart(){
        p1.clear()
        p2.clear()
        //active =1
        //var bu1:Button = b1
        b1.text="X/O"
        b2.text="X/O"
        b3.text="X/O"
        b4.text="X/O"
        b5.text="X/O"
        b6.text="X/O"
        b7.text="X/O"
        b8.text="X/O"
        b9.text="X/O"
        b1.setBackgroundResource(R.color.colorPrimaryDark);
    }
     fun playgame(cellid:Int , buselected:Button){
        if(active==1){
            p1.add(cellid)
            buselected.text="X"
            buselected.setBackgroundColor(Color.BLUE)
            // check if he won
            active=2;
            //autoplay()
        }
         else{

            buselected.text="0"
            buselected.setBackgroundColor(Color.YELLOW)
            p2.add(cellid)

            active=1
        }
         buselected.isEnabled=false
         winner()
     }
     fun winner(){
         var win=-1;
         // row 1
         if((p1.contains(1) && p1.contains(2) && p1.contains(3) )|| (p1.contains(4) && p1.contains(5) && p1.contains(6) )|| (p1.contains(7) && p1.contains(8) && p1.contains(9)))
                 {
                     win = 1
                 }
           else  if((p1.contains(1) && p1.contains(4) && p1.contains(7)) || (p1.contains(2) && p1.contains(5) && p1.contains(8)) || (p1.contains(3) && p1.contains(6) && p1.contains(9))){
                    win =1
                 }
             else if((p1.contains(1) && p1.contains(5) && p1.contains(9)) || (p1.contains(3) && p1.contains(5) && p1.contains(7))){
             win=1
         }
         if((p2.contains(1) && p2.contains(2) && p2.contains(3)) || (p2.contains(4) && p2.contains(5) && p2.contains(6) )|| (p2.contains(7) && p2.contains(8) && p2.contains(9))){
                 win=2
             }
         else  if((p2.contains(1) && p2.contains(4) && p2.contains(7)) || (p2.contains(2) && p2.contains(5) && p2.contains(8)) || (p2.contains(3) && p2.contains(6) && p2.contains(9))){
                 win =2
             }
         else if((p2.contains(1) && p2.contains(5) && p2.contains(9)) || (p2.contains(3) && p2.contains(5) && p2.contains(7))){
             win=2
         }
         if(win!=-1){
             Toast.makeText(this ,"Congrats !!! whewwww ..Player : $win won the game" , Toast.LENGTH_LONG).show()
         }
     }
       fun autoplay(empcell:Int){

           var buselect :Button?
           when(empcell){
               1->buselect=b1
               2->buselect=b2
               3->buselect=b3
               4->buselect=b4
               5->buselect=b5
               6->buselect=b6
               7->buselect=b7
               8->buselect=b8
               9->buselect=b9
               else-> {
                   buselect=b1
               }
           }
           playgame(empcell , buselect)

    }

    fun buacceptevent(view: View) {
        var useremail = etemail.text
        myref.child("Users").child(splitstring(useremail.toString())).child("Request").push().setValue(myemail)
        playeronline(splitstring(useremail.toString()) +splitstring(myemail!!) )
    }
    fun burequestevent(view: View) {
        var useremail = etemail.text.toString()
        myref.child("Users").child(splitstring(useremail)).child("Request").push().setValue(myemail)
       playeronline(splitstring(myemail!!)+ splitstring(useremail!!))
    }
    var sessionID:String?=null
    var playersymbol :String?=null
    fun playeronline(sessionID:String){
        this.sessionID=sessionID
        myref.child("PlayerOnline").removeValue()
        myref.child("PlayerOnline").child((sessionID!!))
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot:  DataSnapshot) {
                    // try to read the data in request child
                    try {
                        p1.clear()
                        p2.clear()

                        val td= dataSnapshot.value as HashMap<String ,Any>
                        if(td!=null){
                            var value: String
                            for(key in td.keys){
                                value=td[key] as String

                                if(value!=myemail){
                                    active= if(playersymbol=="X")1 else 2
                                }else{
                                    active= if(playersymbol=="X")2 else 1
                                }
                                autoplay(key.toInt())

                            }

                        }
                    }catch (ex:Exception){

                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
    }
    var number=0
     fun incomingcalls(){
         myref.child("Users").child(splitstring(myemail!!)).child("Request")
             .addValueEventListener(object : ValueEventListener{
                 override fun onDataChange(dataSnapshot:  DataSnapshot) {
                  // try to read the data in request child
                     try {
                         val td= dataSnapshot.value as HashMap<String ,Any>
                         if(td!=null){
                             var value: String
                             for(key in td.keys){
                                 value=td[key] as String
                                 etemail.setText(value)
                                 // notification v-11
                                 val notifyme=notifications()
                                 notifyme.notify(applicationContext , value + "wants to play TIC-TAC-TOE" , number)
                                 number++
                                 // till here
                                 myref.child("Users").child(splitstring(myemail!!)).child("Request").setValue(true)

                                 break

                             }

                         }
                     }catch (ex:Exception){

                     }
                 }

                 override fun onCancelled(p0: DatabaseError) {

                 }
             })
     }
    fun splitstring(str:String):String{
        var split = str.split("@")
        return split[0]
    }

    //protected fun butclick()
}