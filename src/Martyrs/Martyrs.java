package Martyrs;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class Martyrs implements Comparable<Martyrs>, Comparator<Martyrs> {
	private String name;
	private int age;
	private String location;
	private Date dateOfDeath;
	private char Gender;

	public Martyrs(String name, int age, String location, Date dateOfDeath, char gender) {
		this.name = name;
		this.age = age;
		this.location = location;
		setDateOfDeath(dateOfDeath);
		this.Gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAge() {
		return age;
	}

	private String getNameCompare() {
		return this.name.toLowerCase();
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(dateOfDeath);
	}

	public void setDateOfDeath(Date dateOfDeath) {

		this.dateOfDeath = dateOfDeath;
	}

	public char getGender() {
		return Gender;
	}

	public void setGender(char gender) {
		Gender = gender;
	}

	@Override
//	public String toString() {
//		return getName() + "  " + getAge() + "  " + getLocation() + "  " + (dateOfDeath.getMonth() + 1) + "/"
//				+ dateOfDeath.getDate() + "/" + (dateOfDeath.getYear() + 1900) + "  " + getGender();
//	}

	public String toString() {
		return getName() + "," + getAge() + "," + getLocation() + "," + getDateString() + "," + getGender();
	}

	@Override
	public int compareTo(Martyrs o) {
		return compare(this, o);
	}

	public int compare(Martyrs o1, Martyrs o2) {

		return Comparator.comparing(Martyrs::getNameCompare)
				.thenComparing(Martyrs::getAge)
				.thenComparing(Martyrs::getDateOfDeath)
				.thenComparing(Martyrs::getGender)
				.compare(o1, o2);
	}

}
