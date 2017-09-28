package com.turlington.battlenetTests.beans

import org.hamcrest.CoreMatchers
import org.junit.rules.ErrorCollector

//TODO: send my least favorite co-worker a slack message, e-mail, and postcard upon error

internal fun ErrorCollector.assertFalse(b: Boolean) {
    checkThat(b, CoreMatchers.equalTo(false))
}

internal fun ErrorCollector.assertTrue(b: Boolean) {
    checkThat(b, CoreMatchers.equalTo(true))
}

internal fun ErrorCollector.assertTrue(message: String, b: Boolean) {
    checkThat(message, b, CoreMatchers.equalTo(true))
}

internal fun ErrorCollector.assertEquals(o1: Any?, o2: Any?) {
    checkThat(o1, CoreMatchers.equalTo(o2))
}

internal fun ErrorCollector.assertEquals(o1: Double, o2: Double, fuzz: Double) {
    checkThat("Value $o2 did not match within acceptable fuzz.", Math.abs(o1 - o2) < fuzz, CoreMatchers.equalTo(true))
}