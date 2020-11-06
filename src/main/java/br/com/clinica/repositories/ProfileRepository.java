package br.com.clinica.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.clinica.model.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long>{

	Profile findByNome(String nome);

}
