package com.hongyan.learn.web.servlet;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.spring.extension.SpringExtension;

/**
 * Created with IntelliJ IDEA. User: Victor Weng Date: 16/4/12 Time: 下午3:24 To change this template use File | Settings
 * | File Templates.
 */
public class PebbleEngineFactory {
    public static PebbleEngine instance(Loader<?> loader, SpringExtension springExtension) {
        return new PebbleEngine.Builder().cacheActive(false).loader(loader).extension(springExtension).build();
    }
}
