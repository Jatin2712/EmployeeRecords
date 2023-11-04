package Entities;

public class SkillMaster {

	int skillMasterId;
	int employeeId;
	String name;

	public SkillMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SkillMaster(int employeeId, int skillMasterId, String name) {
		super();
		this.employeeId = employeeId;
		this.skillMasterId = skillMasterId;
		this.name = name;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getSkillMasterId() {
		return skillMasterId;
	}

	public void setSkillMasterId(int skillMasterId) {
		this.skillMasterId = skillMasterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "SkillMaster [skillMasterId=" + skillMasterId + ", employeeId=" + employeeId + ", name=" + name + "]";
	}

}
