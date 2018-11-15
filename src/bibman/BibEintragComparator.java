package bibman;

import java.util.Comparator;

public class BibEintragComparator implements Comparator<BibEintrag> {

	@Override
	public int compare(BibEintrag a, BibEintrag b) {
		return -Integer.compare(a.getJahr(), b.getJahr());
	}
}
