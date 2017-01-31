package com.github.agadar.nstelegram.filter;

import com.github.agadar.nstelegram.filter.abstractfilter.FilterAddOrRemove;
import com.github.agadar.nsapi.NSAPI;
import com.github.agadar.nsapi.domain.region.Region;
import com.github.agadar.nsapi.enums.shard.RegionShard;

import java.util.HashSet;
import java.util.Set;

/**
 * Filter for adding/removing nations in specified regions from the address
 * list.
 *
 * @author Agadar (https://github.com/Agadar/)
 */
public class FilterRegions extends FilterAddOrRemove {

    /**
     * This instance's set of regions to retrieve nations from.
     */
    private final Set<String> Regions;

    public FilterRegions(Set<String> regions, boolean add) {
        super(add);
        this.Regions = regions;
    }

    @Override
    public void refresh() {
        // If we already retrieved data before, do nothing.
        if (nations != null) {
            return;
        }

        // Query global cache. For every region not found in the global cache,
        // retrieve its nations from the server and also update the global cache.
        nations = new HashSet<>();

        Regions.stream().forEach((region) -> {
            // Check if global cache contains the values.
            Set<String> nationsInRegion = GLOBAL_CACHE.getNationsInRegion(region);

            // If not, retrieve them from the server and also update global cache.
            if (nationsInRegion == null) {
                final Region r = NSAPI.region(region)
                        .shards(RegionShard.NationNames).execute();

                // If region does not exist, just add empty map to global cache.
                if (r == null) {
                    GLOBAL_CACHE.mapNationsToRegion(region, new HashSet<>());
                } // Else, do proper mapping.
                else {
                    nationsInRegion = new HashSet<>(r.NationNames);
                    nations.addAll(nationsInRegion);
                    GLOBAL_CACHE.mapNationsToRegion(region, nationsInRegion);
                }
            } else {
                nations.addAll(nationsInRegion);
            }
        });

        cantRetrieveMoreNations = true;
    }
}
