package com.lzw.order.dinnerorderapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LZW on 2017/07/07.
 */

public class AddressInfo {
    @SerializedName("status")
    private String status;
    @SerializedName("OK")
    private String info;
    @SerializedName("regeocode")
    private Regeocode regeocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Regeocode getRegeos() {
        return regeocode;
    }

    public void setRegeos(Regeocode regeo) {
        this.regeocode = regeo;
    }

    public class Regeocode
    {
        @SerializedName("formatted_address")
        public String formatted_address;

        @SerializedName("pois")
        public List<POI> pois;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public List<POI> getPois() {
            return pois;
        }

        public void setPois(List<POI> pois) {
            this.pois = pois;
        }



        public class POI
        {
            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("location")
            private String location;
            @SerializedName("address")
            private String address;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLongitude()
            {
                return location.split(",")[0];
            }

            public String getLatitude()
            {
                return location.split(",")[1];
            }
        }
    }
}
