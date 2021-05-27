/*
 * MIT License
 *
 * © Copyright 2020 Adobe. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.adobe.campaign.tests.integro.phased;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class PhasedDataProvider {
    public static final String SHUFFLED = "phased-data-provider-shuffled";
    public static final String SINGLE = "phased-data-provider-single";
    public static final String DEFAULT = "phased-default";

    @DataProvider(name = SHUFFLED)
    public Object[][] cascaded(Method m) {
        return PhasedTestManager.fetchProvidersShuffled(m);
    }
    
    @DataProvider(name = SINGLE)
    public Object[] single(Method m) {
        return PhasedTestManager.fetchProvidersSingle(m);
    }
    
    @DataProvider(name = PhasedDataProvider.DEFAULT)
    public Object[][] defaultDP() {
        return new Object[][]{{PhasedDataProvider.DEFAULT}};
    }

}
