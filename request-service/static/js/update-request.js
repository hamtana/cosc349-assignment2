/* global Vue, axios */
var requestApi = (name) => `/api/requests/${name}`;
var propertiesApi = (username) => `/api/properties/tenant/${username}`;

const app = Vue.createApp({

    data() {
        return {
            // Models map
            updatedRequest: new Object(),
            property : new Object()
        };
    },

    computed: {
        // Map Vuex state to get the tenant and request from the data store
        ...Vuex.mapState({
            tenant: state => state.tenant,
            request: state => state.request
        })
    },

    mounted() {
        // Assign the tenant from Vuex to the request
        if (this.tenant) {
            this.request.tenant = this.tenant;
        }

        // Assign the stored request to the local updatedRequest object
        if (this.request) {
            this.updatedRequest = { ...this.request };
        }

        this.fetchPropertyDetails();
    },

    methods: {
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
        // Method to update the existing request
        updateRequest() {
            // Ensure urgent and completed are boolean values
            this.updatedRequest.urgent = !!this.updatedRequest.urgent;
            this.updatedRequest.completed = !!this.updatedRequest.completed;
            console.log(this.updatedRequest);

            // Make a PUT request to update the request on the server
            axios.put(requestApi(this.updatedRequest.name), this.updatedRequest)
                .then(() => {
                    alert('Request updated successfully!');
                    window.location = 'view-requests.html';
                })
                .catch(error => {
                    alert(error.response.data.message);
                });
        },

        // Method to delete the request
        deleteRequest() {
            axios.delete(requestApi(this.request.name))
                .then(() => {
                    alert('Request deleted successfully!');
                    window.location = 'view-requests.html';
                })
                .catch(error => {
                    alert(error.response.data.message);
                });
        }
    }

});

// Import navigation menu component
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);

// Import data store
import { dataStore } from './data-store.js';
app.use(dataStore);

// Mount the app
app.mount("main");
