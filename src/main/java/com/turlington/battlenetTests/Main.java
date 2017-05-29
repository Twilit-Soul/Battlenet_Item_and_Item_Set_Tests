package com.turlington.battlenetTests;

class Main {

    /*
        I feel that I ran a risk of over-engineering this, so I just want to state that I'm aware of that.

        I also really want to put the disclaimer here saying that I understand team standards are holy, and I'd
        test this on-the-job however the team likes and agrees upon.

        ~~

        In both the item test and the item set test, I actually call both APIs. This is bad because there's
        a chance that the test for WoWItemAPI could fail due to the WoWItemSetAPI failing. However, it's good because
        I can make sure the number returned actually is that of a real item. I'd want to see what my co-workers thought
        before committing these tests to git/subversion/whatever. We could potentially just log a warning instead of
        actually causing the test to fail, i.e. "This id may not actually correspond to a real item."
     */
}
