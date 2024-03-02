import java.io.*;
import java.util.*;

public class logic1{

	
	public static String input(){    //read the input.txt
		String formula = "";
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("input.txt")); 
            BufferedReader br = new BufferedReader(reader);
            String line = " ";
            while ((line= br.readLine()) != null) {
				formula += line;
				formula += " ";
			}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return formula;
	}
	public static String[] array(int a){  //create k-map
		String[] abcd;
		if(a==8){   //8 represent 8 variables
			abcd = new String[8];
			abcd[0] = "a";
			abcd[1] = "b";
			abcd[2] = "c";
			abcd[3] = "d";
			abcd[4] = "e";
			abcd[5] = "f";
			abcd[6] = "g";
			abcd[7] = "h";
			
		}
		else{
			if(a==9){  //9 represent 9 variables
				abcd = new String[9];
				abcd[0] = "a";
				abcd[1] = "b";
				abcd[2] = "c";
				abcd[3] = "d";
				abcd[4] = "e";
				abcd[5] = "f";
				abcd[6] = "g";
				abcd[7] = "h";
				abcd[8] = "i";
			}
			else{  // represent 10 variables
				abcd = new String[10];
				abcd[0] = "a";
				abcd[1] = "b";
				abcd[2] = "c";
				abcd[3] = "d";
				abcd[4] = "e";
				abcd[5] = "f";
				abcd[6] = "g";
				abcd[7] = "h";
				abcd[8] = "i";
				abcd[9] = "j";
				
			}
		}
		return abcd;
	}
	
	public static void  main(String args[]){
		
		try{       //write things in the output.txt
		File file = new File("output.txt");
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
		int column =1;
		int block=0;
		String []voc;
		String[] binary_number=new String[200];  //store binary number (include don't care)
		String[] decimal_number=new String[200];  //store decimal number (include don't care)
		int count_number=0;     // the count of number (include don't care)
		String[] notcare=new String[30];  //store don't care decimal number
		int count_notcare=0;
		int have_one=0;
		String s,deci;
		String binary;
		String formula = input();  // read the file
		String[] one = formula.split(" ");
		String vary=one[0].substring(1,(one[0].length()-1));
		int variable = Integer.parseInt(vary);
		vary="%0"+vary;
		vary+="d";
		voc=array(variable);
		for(int i=0;i<one.length;i++){     //read the file  catch don't care and decimal          
			if(one[i].charAt(0)!='<'&&one[i].charAt(0)!='['&&one[i].charAt(0)!=']'&&one[i].charAt(0)!=')'){
				if(one[i].charAt(0)!='('&&count_notcare==0){
					decimal_number[count_number]=one[i];
					count_number++;
				}
				else{
					if(count_notcare==0){
						i++;
					}
					String dontcare=one[i];
					decimal_number[count_number]=dontcare;
					count_number++;
					notcare[count_notcare]=dontcare;
					count_notcare++;
				}
			}
		}
		int []have_one_array=new int[count_number];
		for(int i=0;i<count_number;i++){  //transfor decimal to binary 
			binary = Integer.toBinaryString(Integer.parseInt(decimal_number[i]));
			binary_number[i]=String.format(vary,Integer.parseInt(binary));
		}
		
		for(int i=0;i<count_number;i++){  // catch 'one' number in binary
			for(int j=0;j<variable;j++){
				if(binary_number[i].charAt(j)=='1'){
					have_one++;
				}
			}
			have_one_array[i]=have_one;
			have_one=0;
		}
		ArrayList<Integer> seperate_number = new ArrayList<Integer>();//each area has x number
		ArrayList<Integer> seperate_one = new ArrayList<Integer>();  //each area's number has x "1"
		ArrayList<String> order_binary_number = new ArrayList<String>();  //let binary_number in order
		ArrayList<String> order_decimal_number = new ArrayList<String>();  //let decimal_number in order
		ArrayList<Integer> beused = new ArrayList<Integer>();   //choose number which is be used

		
		for(int i=0;i<=variable;i++){   //let binary and decimal in order
			for(int j=0;j<count_number;j++){
				if(have_one_array[j]==i){
					order_binary_number.add(binary_number[j]);
					order_decimal_number.add(decimal_number[j]);
					block++;
				}
				if(j==(count_number-1)&&block!=0){
					seperate_number.add(block);  // the same number of 'one' has "block" 
					seperate_one.add(i);   // there has i "one"
					block=0;
				}
			}
		}
		
		s="";
		deci="";
		int different =0;
		int precise_different=1;
		int compute1=0;
		int compute2=0;
		int compute3=0;
		int truth_have_one=0;
		int beused_count;
		int remove=0;
		have_one=0;
		int count =0;
		// try to fulfill the column write file
			if(seperate_number.size()>1){  // distinguish the seperate more than 1
				for(int i=0;i<(seperate_number.size()-1);i++){  //the seperate number
					compute2+=seperate_number.get(i);
					if(seperate_one.get(i)+1==seperate_one.get(i+1)){  //if the seperate are continuous
						for(int j=0;j<seperate_number.get(i);j++){  // choose first area
							for(int k=0;k<seperate_number.get(i+1);k++){  // choose second area
							s=order_binary_number.get(compute1+j);
							deci=order_decimal_number.get(compute1+j);
							String[] s_split = s.split("");
							s="";
								for(int l=0;l<variable;l++){
									if(order_binary_number.get(j+compute1).charAt(l)!=order_binary_number.get(k+compute2).charAt(l)){
										s_split[l]="-";
										different++;
									}
								}
								for(int l=0;l<s_split.length;l++){
									s+=s_split[l];
								}
								if(different==1){
									beused.add(j+compute1);  //choose which is be used
									beused.add(k+compute2);  //choose which is be used
									beused_count=beused.size();
									for(int l=0;l<(beused_count-2);l++){  // calculate which binary is used
										if(beused.get(l)==j+compute1){	
											beused.remove(beused_count-2);  //remove repeat number in beused
											remove++;
	
										}
										if(beused.get(l)==k+compute2){
											if(remove==0){
											beused.remove(beused_count-1);
											remove++;
											}
											else{
												beused.remove(beused_count-2);
												remove++;
											}
										}
									}
									remove=0;
									order_binary_number.add(s);
									order_decimal_number.add(deci+","+order_decimal_number.get(k+compute2));
									count++;
									for(int l=0;l<s.length();l++){
										if(s.charAt(l)=='1'){
											have_one++;
										}
									}
									if(have_one!=truth_have_one){
										truth_have_one=have_one;
									}
									
								}
								deci="";
								s="";
								different=0;
								have_one=0;
							}
						}
						
						if(count!=0){
							seperate_number.add(count);
							seperate_one.add(truth_have_one);
						}
						count=0;
						
					}
					compute1+=seperate_number.get(i);
					
				}
			}
			
		s="";
		ArrayList<String> choosed_binary = new ArrayList<String>();  //select which binary is be choosed
		
		for(int i=0;i<count_notcare;i++){    //write don't care(d) in writefile
			for(int j=0;j<order_decimal_number.size();j++){
				if(notcare[i].equals(order_decimal_number.get(j))){
					order_binary_number.set(j,("d "+order_binary_number.get(j)));
			}
			
			}
		}
		for(int i=0;i<order_binary_number.size();i++){  //write be used(v) in writefile
			for(int j=0;j<beused.size();j++){
				if(i==beused.get(j)){
					if(order_binary_number.get(i).charAt(0)=='d'){
						continue;
					}
					order_binary_number.set(i,("v "+order_binary_number.get(i)));
				}
			}
		}
		ArrayList<String> choosed_multiple_decimal = new ArrayList<String>();	//select which decimal is be choosed(ex:1   2,18   5,6  ...)
		for(int i=0;i<order_binary_number.size();i++){    //write repeat(x) and used(  ) in writefile 
			for(int j=0;j<order_binary_number.size();j++){
				if(i==j){
					continue;
				}
				if(order_binary_number.get(i).equals(order_binary_number.get(j))){
					if(order_binary_number.get(j).charAt(0)=='x'||order_binary_number.get(j).charAt(0)=='v'){
						continue;
					}
					order_binary_number.set(j,("x "+order_binary_number.get(i)));
				}
			}
			if(order_binary_number.get(i).charAt(0)=='0' ||order_binary_number.get(i).charAt(0)=='1'||order_binary_number.get(i).charAt(0)=='-'){  //if this binary need to print,then write "  "
				choosed_multiple_decimal.add(order_decimal_number.get(i));
				choosed_binary.add(order_binary_number.get(i));  // choose the used binary
				order_binary_number.set(i,("  "+order_binary_number.get(i)));
				s+=order_decimal_number.get(i);
				s+=",";
				
			}
		}
		
		truth_have_one=-1;
		have_one=0;
		count=0;
		different=0;
		fw.append("Column "+column+"\n=====================\n");
		column++;
		for(int i=0;i<order_binary_number.size();i++){   // write the file
			for(int j=0;j<variable;j++){
				if(order_binary_number.get(i).charAt(j+2)=='1'){
					have_one++;
				}
				if(order_binary_number.get(i).charAt(j+2)=='-'){
					count++;
				}
			}
			if(different!=count){
				fw.append("=====================\n"+"Column "+column+"\n=====================\n");
				different=count;
				truth_have_one=have_one;
				column++;
			}
			if(i==0){
				truth_have_one=have_one;
			}
			if(have_one!=truth_have_one){
				fw.append("=====================\n");
				truth_have_one=have_one;
			}
			fw.append(order_binary_number.get(i)+" : "+order_decimal_number.get(i)+"\n");
			have_one=0;
			count=0;
		}
		String[] s_split=s.split(",");
		ArrayList<String> choosed_decimal = new ArrayList<String>(); //select which decimal is be used in image(ex:1   2   6  ...)

		boolean can_be_choose = true;
		for(int i=0;i<s_split.length;i++){
			for(int j=0;j<count_notcare;j++){
				if(notcare[j].equals(s_split[i])){
					can_be_choose=false;
				}
			}
			if(can_be_choose){
				choosed_decimal.add(s_split[i]);
			}
			can_be_choose=true;
		}
		for(int j=0;j<choosed_decimal.size();j++){  // choose decimal be used in image
			for(int i=choosed_decimal.size()-1;i>-1;i--){
				if(i==j){
					continue;
				}
				if(choosed_decimal.get(j).equals(choosed_decimal.get(i))){
					choosed_decimal.remove(i);
				}
			}
		}
		int first_deci=0;
		int second_deci=0;
		String change;
		for(int i=0;i<choosed_decimal.size();i++){  // let choosed_decimal number be in order
			for(int j=0;j<choosed_decimal.size();j++){
				if(i>=j){
					continue;
				}
				first_deci = Integer.parseInt(choosed_decimal.get(i));
				second_deci = Integer.parseInt(choosed_decimal.get(j));
				if(first_deci>second_deci){
					change=choosed_decimal.get(i);
					choosed_decimal.set(i,choosed_decimal.get(j));
					choosed_decimal.set(j,change);
				}
			}
		}
		ArrayList<String> choosed_voc = new ArrayList<String>();  //tranfor choosed_binary into vocbulary
		boolean can_add=true;
		
		for(int i=0;i<choosed_binary.size();i++){    //change binary into vocbulary
			for(int j=0;j<variable;j++){
				if(choosed_binary.get(i).charAt(j)=='-'){
					continue;
				}
				if(choosed_binary.get(i).charAt(j)=='1'){
					if(can_add){
						choosed_voc.add(voc[j]);
						can_add=false;
					}
					else{
						choosed_voc.set(i,choosed_voc.get(i)+voc[j]);
					}
					
				}
				if(choosed_binary.get(i).charAt(j)=='0'){
					if(can_add){
						choosed_voc.add(voc[j]+"'");
						can_add=false;
					}
					else{
						choosed_voc.set(i,choosed_voc.get(i)+voc[j]+"'");
					}
				}
			}
			can_add=true;
		}
		
		s="";
		fw.append("\n");
		String[][] image = new String[choosed_binary.size()][choosed_decimal.size()];  //this is used to the second output--image
		for(int i=0;i<image.length;i++){  // initialize the image
			for(int j=0;j<image[0].length;j++){
				image[i][j]=" ";
			}
		}
		
		
		
		for(int i=0;i<choosed_binary.size();i++){  //input information in image
			s=choosed_multiple_decimal.get(i);
			s_split=s.split(",");
			for(int j=0;j<choosed_decimal.size();j++){
				for(int k=0;k<s_split.length;k++){
					if(s_split[k].equals(choosed_decimal.get(j))){
						image[i][j]="x";
					}
				}
				
			}
		}
		//begin to draw the image
		for(int i=0;i<(22+choosed_decimal.size()*6);i++){
			fw.append("=");
		}
		fw.append("\nResult\n");
		for(int i=0;i<(22+choosed_decimal.size()*6);i++){
			fw.append("=");
		}
		fw.append("\n                     |");
		for(int i=0;i<choosed_decimal.size();i++){
			fw.append(String.format("%5s|",choosed_decimal.get(i)));
			/*System.out.printf("%5s|",choosed_decimal.get(i));*/
		}
		fw.append("\n");
		for(int i=0;i<(22+choosed_decimal.size()*6);i++){
			fw.append("-");
		}
		fw.append("\n");
		for(int i=0;i<image.length;i++){ 
		fw.append(String.format("%-21s",choosed_voc.get(i)));
		/*System.out.printf("%-21s",choosed_voc.get(i));*/
		fw.append("|");
			for(int j=0;j<image[0].length;j++){
				fw.append(String.format("%5s|",image[i][j]));
				/*System.out.printf("%5s|",image[i][j]);*/
			}
			fw.append("\n");
			for(int j=0;j<(22+choosed_decimal.size()*6);j++){
				fw.append("-");
			}
			fw.append("\n");
		}
		int[] prime_implicant=new int[choosed_decimal.size()];  //understand each row's number of prime_implicant
		have_one=0;  //this choose prime_implicant
		for(int i=0;i<image[0].length;i++){
			for(int j=0;j<image.length;j++){
				if(image[j][i].equals("x")){
					have_one++;
				}
			}
			prime_implicant[i]=have_one;
			have_one=0;
		}
		ArrayList<String> final_choosed_voc = new ArrayList<String>();  //the final result
		
		can_add=true;  
		can_be_choose=true;
		count=-1;
		compute1=0;
		int[] voc_prime_implicant_number=new int[image.length];
		while(can_add){  //begin to find the best solution
			if(can_be_choose){
				for(int i=0;i<prime_implicant.length;i++){ 				
					if(prime_implicant[i]==1){    // find essential prime implicant
						for(int j=0;j<image.length;j++){
							if(image[j][i].equals("x")){
								final_choosed_voc.add(choosed_voc.get(j));
								for(int k=0;k<choosed_decimal.size();k++){
									if(image[j][k].equals("x")){
										for(int l=0;l<image.length;l++){
											if(image[l][k].equals("x")){
												image[l][k]="1"; //1 means this is be used
											}
										}
										
										
									}
								}
							}
						}
					}
						
				}
				can_be_choose=false;
			}
			for(int i=0;i<image.length;i++){  //initialize
				voc_prime_implicant_number[i]=0;
			}
			for(int i=0;i<image.length;i++){
				for(int j=0;j<image[0].length;j++){
					if(image[i][j].equals("x")){
						voc_prime_implicant_number[i]++;
						have_one++; // this have_one is distinguish wheather the "x" is all be choosed
					}
				}
			}
			compute1=voc_prime_implicant_number[0];
			count=0;

			
			if(have_one==0){
				can_add=false;
			}
			else{
				for(int i=0;i<voc_prime_implicant_number.length;i++){
					/*System.out.print(i);*/
					if(voc_prime_implicant_number[i]>compute1){
						/*System.out.println("compute1:"+compute1+"voc_prime_implicant_number["+i+"]"+voc_prime_implicant_number[i]);*/
						compute1=voc_prime_implicant_number[i];
						count=i;
					}
				}
				/*System.out.println("compute1:"+compute1+"count"+count);
				System.out.println("=========================================================================================");*/
				final_choosed_voc.add(choosed_voc.get(count));
				for(int i=0;i<image[0].length;i++){
					if(image[count][i].equals("x")){
						for(int j=0;j<image.length;j++){
							if(image[j][i].equals("x")){
								image[j][i]="1";    //1 means this is be used
							}
						}
					}
				}
				
				
				
			}
			have_one=0;
		}
		fw.append("F(");
		for(int i=0;i<voc.length;i++){
			fw.append(voc[i]);
			if(i!=voc.length-1){
				fw.append(",");
			}
			else{
				fw.append(")= ");
			}
		}
		for(int i=0;i<final_choosed_voc.size();i++){  // initialize the image
			fw.append(final_choosed_voc.get(i));
			if(i!=final_choosed_voc.size()-1){
				fw.append(" + ");
			}
		}
		fw.flush();
		fw.close();
		}catch (IOException e) {
            e.printStackTrace();
        }

	}
}