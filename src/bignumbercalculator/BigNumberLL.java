
package bignumbercalculator;

/**
 *
 * @author DejiNerd
 */
public class BigNumberLL {

    public Link first; // reference to first Link
    public Link last; // reference to last Link
    private int size=0; // size of the number (#Links)
    private boolean positive=true; // sign of the bignumber
    
    // simple constructor
    BigNumberLL() {
        first=null;
        last=null;
}
    // a constructor that takes a string and converts it into a BigNumber
    BigNumberLL(String s) {
        int count=0;
        if(s.charAt(0)=='-'){
            positive = false;
            
            for(int i = 1; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))){    
            insertLast( Character.getNumericValue(s.charAt(i))); count++; }
            }
        }
        else{
            for(int i = 0; i<s.length(); i++){
                if(Character.isDigit(s.charAt(i))){ 
                insertLast( Character.getNumericValue(s.charAt(i))); count++; }
            }
        }
        size = count;
    }
    ///// All Methods to follow
    
    public void insertLast(int no){
        
        Link newLast =  new Link(no);
        
        if(last!=null){
            last.next = newLast;
            newLast.prev = last;
            last = newLast;
            last.next = null;
        }
        
        else {
            last= newLast;
            first = newLast;
            
        }
        
    }
    
    public void insertFirst(int no){
        Link newFirst = new Link(no);
        if(first!= null){
        newFirst.next = first;
        first.prev = newFirst;
        newFirst.prev = null;
        first = newFirst;
        }
        else{
            first = newFirst;
            last = newFirst;
        }
    }
    
    public void deleteFirst(){
        first = first.next;
        first.prev = null;
    }
    
    public void trimFront (){
        Link current = first;
        while(current.val == 0){
            deleteFirst();
            current = current.next;
        }
    }
    
    public boolean isEmpty(){
        return first == null;
    }
    
    //gets the size
    public int getSize(){
        return size;
    }
    
    //flips the sign
    public void setSign(){
        positive = !positive;
    }
    
    //gets the sign
    public boolean getSign(){
        return positive;
    }
//    
//    public String toStringNoCommasSign(){
//        Link current  = last;
//        int tracker = 0;
//        String str = "";
//        while(current!=null){
//            
//            str= current.val+ str;
//            tracker++;
//            current= current.prev;
//        }
//        
//        return str;
//    }
    public String toStringNoCommas(){
        Link current  = last;
        int tracker = 0;
        String str = "";
        while(current!=null){
            
            str= current.val+ str;
            tracker++;
            current= current.prev;
        }
        if(!positive){
                str="-"+str;
            }
        return str;
    }
    
    @Override
    public String toString(){
        trimFront();
        Link current  = last;
        int tracker = 0;
        String str = "";
        while(current!=null){
            
            if(tracker!= 0 && tracker%3 == 0){
                str=","+str;
            }
            str= current.val+ str;
            tracker++;
            current= current.prev;
        }
        if(!positive){
                str="-"+str;
            }
        return str;
    }
    
    // this method compares two values without considering the sign
    static int compareNoSign(BigNumberLL big1, BigNumberLL big2){
            if(big1.size>big2.size){return 1;}
            else if(big1.size<big2.size){return -1;}
            else if(big1.size==big2.size){
                Link current1,current2;
                current1 = big1.first;
                current2 = big2.first;
                while(current1!=null &&current2!=null){
                    if(current1.val>current2.val){return 1;}
                    else if(current1.val<current2.val){return -1;}
                    else{
                        current1= current1.next;
                        current2= current2.next;
                    }
                }
            }
        return 0;
    }
    static int compare(BigNumberLL big1, BigNumberLL big2) {
        //check the signs first
        if(big1.getSign()== true && big2.getSign()== false){return 1;}
        if(big2.getSign()== true && big1.getSign()== false){return -1;}
        // both have positive signs
        if(big1.getSign()== true && big2.getSign()== true){
            //compare the size
            if(big1.size>big2.size){return 1;}
            else if(big1.size<big2.size){return -1;}
            //same size? compare every digit, starting from the first
            else if(big1.size==big2.size){
                Link current1,current2;
                current1 = big1.first;
                current2 = big2.first;
                while(current1!=null &&current2!=null){
                    if(current1.val>current2.val){return 1;}
                    else if(current1.val<current2.val){return -1;}
                    else{
                        current1= current1.next;
                        current2= current2.next;
                    }
                }
            }
        }
        //both have negative signs
        if(big1.getSign()== false && big2.getSign()== false){
            //compare the size, the smaller the size the bigger the value in a negative case
            if(big1.size<big2.size){return 1;}
            else if(big1.size>big2.size){return -1;}
            else if(big1.size==big2.size){
                Link current1,current2;
                current1 = big1.first;
                current2 = big2.first;
                while(current1!=null &&current2!=null){
                    if(current1.val<current2.val){return 1;}
                    else if(current1.val>current2.val){return -1;}
                    else{
                        current1= current1.next;
                        current2= current2.next;
                    }
                }
            }
        }
        return 0; // returns zero if they're equal
    }

    static BigNumberLL add(BigNumberLL big1, BigNumberLL big2) {
        BigNumberLL addy = new BigNumberLL();
        
        //handles the addition of a -ve no to a +ve no by calling the subtraction method, e.g : -20+30
        if(big1.getSign()==false && big2.getSign()==true && compareNoSign(big1,big2)== -1){
           addy =  subtract(big2,big1);
           return addy;
        }
        //handles a case like 30+(-20)
        else if (big2.getSign()==false && big1.getSign()==true){
            addy =  subtract(big1,big2);
           return addy;
        }
        
        //handles the addition of two negative numbers
        // does normal addition then changes the sign at the end
        else if(big1.getSign()==false && big2.getSign()==false){
            if(big1.size>big2.size){
            for(int i = big2.size; i<big1.size;i++){
                big2.insertFirst(0);
            }
            }
            else if(big2.size>big1.size){
            for(int i = big1.size; i<big2.size;i++){
                big1.insertFirst(0);
            }
            }
            Link current1 = big1.last;
            Link current2 = big2.last;
            while(current1!=null&& current2!=null){
            if(current1.val+current2.val > 9){
                int y = (current1.val+current2.val)-10;
                addy.insertFirst(y);
                current1 = current1.prev;
                if(current1 ==null){
                    addy.insertFirst(1);
                }
                else{
                current1.val = current1.val+1;
                current2 = current2.prev;
                }
                
            }
            else{
              int z = current1.val+current2.val;
              addy.insertFirst(z);  
              current1 = current1.prev;
              current2 = current2.prev;
            }
        }
        addy.setSign(); 
        return addy;
        }
        
        if(big1.size>big2.size){
            for(int i = big2.size; i<big1.size;i++){
                big2.insertFirst(0);
            }
        }
        else if(big2.size>big1.size){
            for(int i = big1.size; i<big2.size;i++){
                big1.insertFirst(0);
            }
        }
        
        Link current1 = big1.last;
        Link current2 = big2.last;
        
        while(current1!=null&& current2!=null){
            if(current1.val+current2.val > 9){
                int y = (current1.val+current2.val)-10;
                addy.insertFirst(y);
                current1 = current1.prev;
                if(current1 ==null){
                    addy.insertFirst(1);
                }
                else{
                current1.val = current1.val+1; //adds the carryon to the next value if the sum of the current values > 9
                current2 = current2.prev;
                }
                
            }
            else{
              int z = current1.val+current2.val;
              addy.insertFirst(z);  
              current1 = current1.prev;
              current2 = current2.prev;
            }
        }
        return addy;
    }

    static BigNumberLL subtract(BigNumberLL big1, BigNumberLL big2) {
        BigNumberLL minus = new BigNumberLL();
        if(big1.size>big2.size){
            for(int i = big2.size; i<big1.size;i++){
                big2.insertFirst(0);
            }
        }                                            //adds extra 0s to balance the size of the numbers
        else if(big2.size>big1.size){
            for(int i = big1.size; i<big2.size;i++){
                big1.insertFirst(0);
            }
        }
        if(big1.getSign()==false && big2.getSign()==true){ //handles a case like this (-20)-(30) = -50
            big1.setSign();
            minus = add(big1,big2);
            minus.setSign();
            return minus;
        }
        if(big1.getSign()==false && big2.getSign()==false){ //handles a case like this (-30)-(-40) = 10
            big1.setSign();
            big2.setSign();
            minus = subtract(big2,big1);
            return minus;
        }
        if(compareNoSign(big1,big2)==-1){ // handles a case like this "350-600" = -(600-350)
         
        
        Link current1 = big2.last;    // Switches the order of our number, our first no becomes the largest number amongst the two
        Link current2 = big1.last;
        
        while(current1!=null && current2!=null){
            if(current1.val-current2.val < 0){
                minus.insertFirst((10+current1.val)-current2.val);
                current1=current1.prev;
                current1.val = current1.val-1;
                current2 = current2.prev;
            }
            else{
                minus.insertFirst(current1.val-current2.val);
                current1 = current1.prev;
                current2 = current2.prev;
            }
            
        }
            minus.setSign();
        }
        
        
        else{
        
        
        Link current1 = big1.last;
        Link current2 = big2.last;
        
        while(current1!=null && current2!=null){
            if(current1.val-current2.val < 0){
                minus.insertFirst((10+current1.val)-current2.val); // deals with the borrowing case 
                current1=current1.prev;
                current1.val = current1.val-1; //subtracts one from the digit that was borrowed from
                current2 = current2.prev;
            }
            else{
                minus.insertFirst(current1.val-current2.val);
                current1 = current1.prev;
                current2 = current2.prev;
            }
        } 
    }
        return minus;
    }
    // this method is never used in the code, just created it because the question asked to.
    static BigNumberLL multiplyBase(BigNumberLL big1, int x){
        Link current1;
        int temp = 0;
        current1 = big1.last;
        BigNumberLL base = new BigNumberLL();
        while(current1 != null){
         if((current1.val * x) +temp > 9){   
               int y = (current1.val * x)+ temp;
               String hold = ""+ (y);
               base.insertFirst(Character.getNumericValue(hold.charAt(1)));
               temp = Character.getNumericValue(hold.charAt(0));
               if(current1.prev == null){
                    base.insertFirst(temp);
               }
               
               
               current1=current1.prev;
               
           }
         else{
               int y = (current1.val * x)+ temp;
               base.insertFirst(y);
               current1=current1.prev;
               temp = 0;
           }
        }
        return base;

    }
    
    static BigNumberLL multiply(BigNumberLL big1, BigNumberLL big2) {
        BigNumberLL [] times = new BigNumberLL[30]; // Stores each step of the multiplication process 
        BigNumberLL keep = new BigNumberLL(); // holds the addition process
        Link current1;   
        Link current2;
        int temp = 0; // stores the carry ons
        
        current1 = big1.last;
        current2 = big2.last;
            
        
       int i = 0;
       int sum = 0;
       while(current2!=null){     // to while loops to handle a case like this: 256 * 765 = 5(256)+ 60(256) + 700(256) 
           while(current1!=null){
           
           if((current1.val * current2.val) +temp > 9){   
               int y = (current1.val * current2.val)+ temp;
               String hold = ""+ (y);
               if(times[i]== null){ times[i] = new BigNumberLL();}
               times[i].insertFirst(Character.getNumericValue(hold.charAt(1)));  // if theres result greater than 9, insertfirst the unit digit and carry the tens digit
               times[i].size= times[i].size+1; //increase  the size, anytime we insert increase the size 
               temp = Character.getNumericValue(hold.charAt(0));
               if(current1.prev == null){
                    times[i].insertFirst(temp);
                    times[i].size= times[i].size+1;
               }
               
               
               current1=current1.prev;
               
           }
           
           else{

               int y = (current1.val * current2.val)+ temp;
               if(times[i]== null){ times[i] = new BigNumberLL();}
               times[i].insertFirst(y);
               times[i].size= times[i].size+1;
               current1=current1.prev;
               temp = 0;
           }
       }
           if(i!=0){
//               System.out.println(i);
               for(int j=1; j<=i;j++){
                   times[i].insertLast(0);
                   times[i].size= times[i].size+1;
               }
           }
           if(i==0){
               keep= times[i];
            }
           else{
              keep = BigNumberLL.add(times[i], keep); // adds after every step of the multiplication process
           }
           i++;
           current1 = big1.last;
           current2 = current2.prev;
           temp = 0;
       }
       if(big1.getSign()==true && big2.getSign()==false){keep.setSign();} // + * - = - 
       if(big2.getSign()==true && big1.getSign()==false){keep.setSign();} // + * - = - 
       return keep;

    }
}
