package app.repository;

import com.animals.app.domain.Animal;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


/**
 * Created by Rostyslav.Viner on 07.08.2015.
 */
public class TestCase {
    private static AnimalRepositoryImpl animalRepositoryImpl = new AnimalRepositoryImpl();;

    @Test
    public void test01Insert() {
        Animal expected = animalRepositoryImpl.getById(70);
        Animal actual = animalRepositoryImpl.getById(70);

        System.out.println(expected.getAddress().equals(actual.getAddress()));
        /*if (expected.getAddress().equals(actual.getAddress())) {
            System.out.print("gg");
        }*/

        assertEquals(expected, actual);
    }
}
