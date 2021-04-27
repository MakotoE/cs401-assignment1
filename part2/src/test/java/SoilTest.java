import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class SoilTest {

	@RunWith(Parameterized.class)
	public static class drains {

		@Parameterized.Parameters
		public static Collection<Object> data() {
			return Arrays.asList(new Object[][] {
				{ // 0
					0,
					new Pair[]{},
					false,
				},
				{ // 1
					1,
					new Pair[]{},
					true,
				},
				{ // 2
					2,
					new Pair[]{},
					false,
				},
				{ // 3
					2,
					new Pair[]{
						new Pair(
							Soil.coordinate_to_id(2, 0, 0),
							Soil.coordinate_to_id(2, 1, 0)
						),
					},
					false,
				},
				{ // 4
					2,
					new Pair[]{
						new Pair(
							Soil.coordinate_to_id(2, 0, 0),
							Soil.coordinate_to_id(2, 0, 1)
						),
					},
					true,
				},
				{ // 5
					3,
					new Pair[]{},
					false,
				},
				{ // 6
					3,
					new Pair[]{
						new Pair(
							Soil.coordinate_to_id(3, 0, 0),
							Soil.coordinate_to_id(3, 0, 1)
						),
						new Pair(
							Soil.coordinate_to_id(3, 0, 1),
							Soil.coordinate_to_id(3, 0, 2)
						),
					},
					true,
				},
			});
		}

		@Parameterized.Parameter
		public int width;

		@Parameterized.Parameter(1)
		public Pair[] connections;

		@Parameterized.Parameter(2)
		public boolean expected;

		@Test
		public void test() {
			assertEquals(expected, Soil.drains(width, connections));
		}
	}
}
