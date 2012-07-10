// Word Fun - wordFun.java
// Created By Prashanth Rajaram [ Contact: http://goo.gl/PlUvv ]
// Version 1.0, Copyright © 2009

import java.util.*;
import java.io.*;
import java.lang.*;

public class wordFun
{
	/* Required Program Variables */
	static String fileName = "words.txt"; //Default name for word list file
	static File file = null;
	static FileReader fr = null;
	static BufferedReader br = null;
	static byte[] b = null;
	static int fileSize = 0;
	static int lineCount = 0;
	List<String> wordList = new ArrayList<String>();
	
	final int chances = 10;
	static int chancesLeft = 0;
	char buffer[] = new char[100];
	boolean check[] = new boolean[100];
	char letters[] = new char[100];
	
	static String chosen = null;
	char ch;
	static int parser = 1;
	//static int hits = 0;
	static int letterIndex=0;		
	Scanner input = new Scanner(System.in);
	
	wordFun(String fn) throws Exception
	{
		file = new File(fn);
		fr = new FileReader (file);
		br = new BufferedReader(fr);
		fileSize = (int) file.length();
		chancesLeft = 10;
		chosen = null;
		letterIndex=0;
		lineCount = 0;
	}

	/*	Intitalization Method - Calls associated methods in sequential order */
	void init() throws Exception
	{
		read();
		initDisplay();
		store();
		guess();
	}
	
	/*	Read Method	- Reads input file and generates word list */
	void read() throws Exception
	{
		String next;
				
		while((next = br.readLine()) != null)
		{
			wordList.add(next);
			++lineCount;			
		}		
	}
	
	/*	Display Initialization Method - Displays question & output to the user */
	void initDisplay() throws Exception
	{
		int r = 0;
		Random generator = new Random();		
		r = generator.nextInt(lineCount-1);
		
		System.out.println("A word has been chosen at random: ");
		chosen=wordList.get(r);
				
		for(int index=0;index<chosen.length();index++)
		{
			System.out.print("_ ");
		}
		System.out.println(" ");
		System.out.println("Chances Left: " + chancesLeft);
		System.out.println("Guess the word, one letter at a time! Go... ");
	}
	
	/*	Store Method - Stores user's input */
	void store()
	{
		int index = 0,grow = 0;
		
		for(index=0;index<chosen.length();index++)
		{
			buffer[grow] = chosen.charAt(index);
			grow++;
		}
		
		for(index=0;index<chosen.length();index++)
		{
			check[index] = false;
		}
	}
	
	/*	Checker Method - Performs checks against the chosen word */
	int checker()
	{
		int index,count = 0;
		for(index=0;index<chosen.length();index++)
		{
			if(check[index] == true)
				count++;
		}
		if(count == chosen.length())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	/*	Guess Method - Compares user guesses */
	void guess() throws Exception
	{
		int index = 0,flag=0,hits=0,done=0;
		done = checker();	
				
		if(done != 1)
		{	
			ch = input.nextLine().charAt(0);
			System.out.println(" ");
		
			for(index=0;index<chosen.length();index++)
			{
				if(letters[index] == (char) ch)
				{
					flag = 1;
					System.out.println("You have already tried that letter! ");
					break;
				}			
			}
			if(flag == 0)
			{
				letters[letterIndex++] = (char) ch;
				for(index=0;index<chosen.length();index++)
				{
					if(ch == buffer[index])
					{
						check[index] = true;
						hits++;
					}			
				}
						
				if(hits == 0)
				{
					chancesLeft--;
					display();
				}
				else
				{
					display();
				}
			}
		
			if(chancesLeft > 0)
			{
				guess();
			}
			else
			{
				System.out.println("\nSorry! You ran out of chances before you could guess the word.");
				System.out.println("Word was: [" + chosen + "] ");
			}			
		}
		else
		{
			System.out.println("Congratulations! You guessed the word correctly [" + chosen + "]");
		}
	}	
	
	/*	Display Method - Shows user output after ever guess after performing checks */
	void display() throws Exception
	{
		int index=0;
		for(index=0;index<chosen.length();index++)
		{
			if(check[index] == true)
			{
				System.out.print(buffer[index] + " ");
			}
			else
			{
				System.out.print("_ ");
			}
		}
		
		System.out.println("\nChances Left: " + chancesLeft);
		System.out.print("Letters Tried: ");
		for(index=0;index<letterIndex;index++)
		{
			System.out.print(letters[index]+", ");
		}
		System.out.println(" ");
				
	}
	
	/*	Main Method - Default Method */
	public static void main(String args[]) throws Exception
	{
		System.out.println("\n-----[WORD FUN]------ ");
		System.out.println("\n    Version: 1.0    \n");
				
		if(args.length == 0)
		{
			wordFun w = new wordFun(fileName);
			w.init();
		}
		else
		{
			wordFun w = new wordFun(args[0]);
			w.init();
		}
		while(true)
		{
			System.out.println("\nDo you want to try your luck at another word? [Y/N] ");
			char choice = (new Scanner(System.in)).nextLine().charAt(0);
			if(choice == 'N' || choice == 'n')
			{
				System.out.println("\n--- Created By Prashanth Rajaram ---\n");
				System.out.println("Word List Source: http://www.langmaker.com/wordlist/basiclex.htm");
				System.exit(0);
			}
			else if(choice == 'Y' || choice == 'y')
			{
				wordFun w2 = new wordFun(fileName);
				w2.init();
			}
		}
		
	}
	
}