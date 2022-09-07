package io.accumulatenetwork.sdk.generated.apiv2;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("VersionResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VersionResponse implements Marhallable {
	private String version;
	private String commit;
	private boolean versionIsKnown;
	private boolean isTestNet;

    //
	public String getVersion() {
	    return version;
	}
	public void setVersion(final String value) {
	    this.version = value;
	}

	public VersionResponse version(final String value) {
	    setVersion(value);
	    return this;
	}
	public String getCommit() {
	    return commit;
	}
	public void setCommit(final String value) {
	    this.commit = value;
	}

	public VersionResponse commit(final String value) {
	    setCommit(value);
	    return this;
	}
	public boolean getVersionIsKnown() {
	    return versionIsKnown;
	}
	public void setVersionIsKnown(final boolean value) {
	    this.versionIsKnown = value;
	}

	public VersionResponse versionIsKnown(final boolean value) {
	    setVersionIsKnown(value);
	    return this;
	}
	public boolean getIsTestNet() {
	    return isTestNet;
	}
	public void setIsTestNet(final boolean value) {
	    this.isTestNet = value;
	}

	public VersionResponse isTestNet(final boolean value) {
	    setIsTestNet(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.version == null || this.version.length() == 0)) {
            marshaller.writeString(1, this.version);
        }
        if (!(this.commit == null || this.commit.length() == 0)) {
            marshaller.writeString(2, this.commit);
        }
        if (!(!this.versionIsKnown)) {
            marshaller.writeBool(3, this.versionIsKnown);
        }
        if (!(!this.isTestNet)) {
            marshaller.writeBool(4, this.isTestNet);
        }
        return marshaller.array();
    }
}