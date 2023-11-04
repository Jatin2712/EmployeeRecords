package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Entities.SkillMaster;

public class SkillMasterDao {

	private Connection connection;

	public SkillMasterDao(Connection connection) {
		super();
		this.connection = connection;
	}

	public boolean addSkill(SkillMaster skillmaster) {
		boolean flag = false;
		try {
			String qurey = "insert into dbo.jbSkillMaster(skillMasterId, name) values(?,?)";

			PreparedStatement preparedstatement = connection.prepareStatement(qurey);

			preparedstatement.setInt(1, skillmaster.getSkillMasterId());
			preparedstatement.setString(2, skillmaster.getName());

			int i = preparedstatement.executeUpdate();

			if (i == 1) {
				flag = true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return flag;
	}

	public List<SkillMaster> getAllSkills() {
		List<SkillMaster> list = new ArrayList<SkillMaster>();
		SkillMaster skillmaster = null;
		try {
			String query = "select * from dbo.jbSkillMaster";
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			ResultSet resultset = preparedstatement.executeQuery();
			while (resultset.next()) {
				skillmaster = new SkillMaster();
				skillmaster.setSkillMasterId(1);
				skillmaster.setName(resultset.getString(2));
				list.add(skillmaster);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean updateSkill(SkillMaster skillMaster, Connection connection2) {
		// TODO Auto-generated method stub
		return false;
	}
}