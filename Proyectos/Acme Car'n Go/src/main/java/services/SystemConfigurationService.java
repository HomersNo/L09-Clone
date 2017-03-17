
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	//managed repository---------------------
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	//supporting services -------------------

	@Autowired
	private AdministratorService			adminService;


	//Basic CRUD methods --------------------
	public SystemConfiguration create() {
		SystemConfiguration created;
		created = new SystemConfiguration();
		return created;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		this.adminService.checkAdministrator();
		SystemConfiguration retrieved;
		retrieved = this.systemConfigurationRepository.findOne(systemConfigurationId);
		return retrieved;
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;
		result = this.systemConfigurationRepository.findAll();
		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		this.adminService.checkAdministrator();
		SystemConfiguration saved;
		saved = this.systemConfigurationRepository.save(systemConfiguration);
		return saved;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	//Auxiliary methods ---------------------

	//Our other bussiness methods -----------
	public SystemConfiguration findMain() {
		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.findMain();
		return systemConfiguration;
	}

	public String getBanner() {
		final String res = this.systemConfigurationRepository.getBanner();
		return res;
	}

}
