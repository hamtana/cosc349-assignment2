/* global Vue, axios */
var requestApi = '/api/requests';
var propertiesApi = (username) => `/api/properties/tenant/${username}`;

const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            request: new Object(),
            property : new Object()
        };
    },

        computed: {
            // Map Vuex state to get the tenant from the data store
            ...Vuex.mapState({
                tenant: state => state.tenant
            })
        },

    mounted() {
        // semicolon separated statements
        if (this.tenant) {
            this.request.tenant = this.tenant;
        }
        this.fetchPropertyDetails()
    },

    methods: {
        // comma separated function declarations

                // Function to fetch property details using tenant username
        async fetchPropertyDetails() {
            if (this.tenant && this.tenant.username) {
                axios.get(propertiesApi(this.tenant.username))
                     .then(response => {
                            // Assuming the property details are the same for all requests
                                const propertyDetails = response.data;
                                console.log('Fetched property details:', propertyDetails);
                                this.property = propertyDetails;
                            });
                    }
                },
        async addRequest() {
        // Ensure that urgent and completed are always boolean values
            this.request.urgent = !!this.request.urgent;
            this.request.completed = !!this.request.completed;

         // Make sure tenant and property details are available
             if (this.tenant && this.tenant.username) {
                   this.request.tenant = this.tenant;  // Set tenant's username
             } else {
                  alert("Tenant is not available");
                  return;
             }

            if (this.property && this.property.address) {
                        this.request.property = this.property; // Assign property address to the request
                    } else {
                        alert("Property details could not be fetched");
                        return;
                    }

            axios.post(requestApi, this.request)
                    .then(() => {
                        window.location = 'view-requests.html';
                    })
                    .catch(error => {
                        alert(error.response.data.message);
                    });
        }


    },

    // other modules
    mixins:[]

});

// other component imports go here



// import navigation  menu component
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);

import { dataStore } from './data-store.js';
app.use(dataStore);


// mount the page - this needs to be the last line in the file
app.mount("main");
