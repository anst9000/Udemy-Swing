package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {

	private List<Person> people;

	public Database() {
		people = new LinkedList<>();
	}

	public void addPerson( Person person ) {
		people.add( person );
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList( people );
	}

	public void saveToFile( File file ) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream( file );
		ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream );

		Person[] personArray = people.toArray( new Person[people.size()] );

		objectOutputStream.writeObject( personArray );

		objectOutputStream.close();
	}

	public void loadFromFile( File file ) throws IOException {
		FileInputStream fileInputStream = new FileInputStream( file );
		ObjectInputStream objectInputStream = new ObjectInputStream( fileInputStream );

		try {
			Person[] personArray = (Person[]) objectInputStream.readObject();
			people.clear();
			people.addAll( Arrays.asList( personArray ) );
		} catch ( ClassNotFoundException | IOException e ) {
			e.printStackTrace();
		}

		objectInputStream.close();
	}

	public void removePerson( int index ) {
		people.remove( index );
	}
}
