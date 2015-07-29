package app.repository;

import com.animals.app.domain.AnimalCitesType;
import com.animals.app.repository.Impl.AnimalCitesTypeRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;


/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class TestAnimalCitesRepositoryImpl {

    private static AnimalCitesTypeRepositoryImpl animalCitesTypeRepositoryImpl;

    @Before
    public void runBeforeClass() {
        animalCitesTypeRepositoryImpl = new AnimalCitesTypeRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        animalCitesTypeRepositoryImpl = null;
    }

    @Test
    public void testGetAll() {
        List<AnimalCitesType> list = animalCitesTypeRepositoryImpl.getAll();

        assertNotNull(list);
    }

    @Test
    public void testGetById() {
        AnimalCitesType expected = animalCitesTypeRepositoryImpl.getById(1);

        assertNotNull(expected);
    }
}
