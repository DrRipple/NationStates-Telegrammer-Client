package com.github.agadar.nstelegram.filter;

import com.github.agadar.nationstates.NationStates;
import com.github.agadar.nationstates.domain.world.World;
import com.github.agadar.nationstates.shard.WorldShard;
import com.github.agadar.nstelegram.filter.abstractfilter.FilterRegionByTags;

import java.util.Set;

/**
 * Filter for adding/removing nations that are in regions with the supplied
 * tags.
 *
 * @author Agadar (https://github.com/Agadar/)
 */
public class FilterRegionsWithTags extends FilterRegionByTags {

    public FilterRegionsWithTags(Set<String> tags, boolean add) {
        super(tags, add);
    }

    @Override
    protected World getWorld() {
        return NationStates.world(WorldShard.REGIONS_BY_TAG).regionsWithTags(tags
                .toArray(new String[tags.size()])).execute();
    }
}
