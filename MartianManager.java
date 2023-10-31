package prob3;
import java.util.ArrayList;
import java.util.Collections;

public class MartianManager {
	protected ArrayList<Martian> martians = new ArrayList<>();
	protected ArrayList<Teleporter> teleporters = new ArrayList<>();
	
	public MartianManager() {}
	
	// Add Martian to ArrayList martians
	public boolean addMartian(Martian m) {
		if(!martians.contains(m)) {
			martians.add(m);
			if(m instanceof Teleporter t) {
				teleporters.add(t);
			}
			return true;
		}
		return false;
	}
	
	// Commence a battle between an accepted list of Martian invaders and the current list of Martians
	// Deaths are determined by power calculations-- killed Martians are stored and returned in a list, and removed from the list of Martians
	public ArrayList<Martian> battle(ArrayList<Martian> invaders) {
		ArrayList<Martian> killList = new ArrayList<>();
		for(Martian i : invaders) {
			for(Martian m : martians) {
				if(getPower(i) > getPower(m)) {
					killList.add(m);
					martians.remove(m);
					break;
				}
			}
		}
		return killList;
	}
	
	// Check if a Martian with a certain id already exists in the list
	public boolean contains(int id) {
		Martian dummy = new GreenMartian(id, 0);
		if(!martians.contains(dummy)) {
			return true;
		}
		return false;
	}
	
	
	// Returns the Martian at the ith index, if valid
	public Martian getMartianAt(int i) {
		if(i<=0 || i>martians.size()) {
			return null;
		}
		return martians.get(i);
	}
	
	// Returns the Martian closest to the given id
	public Martian getMartianClosestTo(Martian martian) {
		int id = martian.getId();
		return getMartianClosestTo(id);
	}
	
	public Martian getMartianClosestTo(int id) {
		int closestDiff = Integer.MAX_VALUE;
		Martian closestM = null;
		for(Martian m : martians) {
			if(Math.abs(m.getId() - id) < closestDiff) {
				closestDiff = Math.abs(m.getId() - id);
				closestM = m;
			}
		}
		return closestM;
	}
	
	// Returns the Martian with a specified id, if valid
	public Martian getMartianWithId(int id) {
		if(contains(id) == true) {
			return null;
		}
		GreenMartian dummy = new GreenMartian(id, 0);
		int index = martians.indexOf(dummy);
		return martians.get(index);
	}
	
	// Returns number of Martians in the list martians
	public int getNumMartians() {
		return martians.size();
	}
	
	// Returns number of Teleporters in the list martians
	public int getNumTeleporters() {
		int num = 0;
		for(Martian m : martians) {
			if(m instanceof GreenMartian gm) {
				num++;
			}
		}
		return num;
	}
	
	// Sort Martians by id
	public ArrayList<Martian> getSortedMartians() {
		ArrayList<Martian> sorted = new ArrayList<>(martians);
		Collections.sort(sorted);
		return sorted;
		
	}
	
	// Returns the teleporter at the ith index
	public Teleporter getTeleporterAt(int i) {
		if(i<=0 || i > teleporters.size()) {
			return null;
		}
		return teleporters.get(i);
	}
	
	// Returns a string of all Martians speaking at once
	public String groupSpeak() {
		String msg = "";
		for(Martian m : martians) {
			msg += m.speak() + "\n";
		}
		return msg;
	}
	
	// Returns a string of all Teleporters going to their destination
	public String groupTeleport(String dest) {
		String msg = "";
		for(Teleporter t : teleporters) {
			msg += t.teleport(dest) + "\n";
		}
		return msg;
	}
	
	// Removes all Teleporters from the martians and teleporters lists
	public void obliterateTeleporters() {
		for(Teleporter t : teleporters) {
			int loc = martians.indexOf(t);
			if(loc >= 0) {
				martians.remove(loc);
			}
		}
		teleporters.clear();
	}
	
	public Martian removeMartian(int id) {
		if(contains(id) == true) {
			return null;
		}
		Teleporter dummy = new GreenMartian(id, 0);
		if(teleporters.contains(dummy)) {
			teleporters.remove(dummy);
		}
		return martians.remove(id);
	}
	
	public void absorbColony(ArrayList<Martian> colony) {
		martians.addAll(colony);
	}
	
	public ArrayList<Martian> captureInvaders(ArrayList<Martian> invaders) {
		ArrayList<Martian> captured = new ArrayList<>();
		int totalPow = 0;
		for(Martian m : martians) {
			totalPow += getPower(m);
		}
		int avgPow = totalPow / getNumMartians();
		for(Martian i : invaders) {
			if(getPower(i) > avgPow) {
				martians.add(i);
				captured.add(i);
			}
		}
		return captured;
	}
	
	
	// Helper method for battle
	public int getPower(Martian m) {
		return m.power();
	}
	
	@Override
	public String toString() {
		String msg = "Martians:\n";
		for(Martian m : martians) {
			msg += m.toString() + "\n";
		}
		msg += "\nTeleporters:\n";
		for(Martian m : martians) {
			if(m instanceof Teleporter t) {
				msg += t.toString() + "\n";
			}
		}
		return msg;
	}

}
