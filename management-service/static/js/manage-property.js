/* global Vue, axios */
var propertiesApi = '/api/properties';
var propertiesApiAddress = (address) => `/api/properties/${address}`;
var managerApi = (username) => `/api/managers/${username}`;

var requestAiTenant = (username) => `/api/requests/tenant/${username}`;

const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            updateProperty : new Object(),
        };
    },

        computed: {
            // Map Vuex state to get the tenant from the data store
            ...Vuex.mapState({
                manager: state => state.manager,
                property : state => state.property
            })
        },

    mounted() {

       // check if session storage is working...
       console.log("Manager in Vuex:", this.manager);
       console.log("Property in Vuex:", this.property);

        // semicolon separated statements
        if (this.manager) {
            this.property.manager = this.manager;
        }
        if (this.property) {
            this.updateProperty = this.property;
        }
    },

    methods: {
        // comma separated function declarations
        manageProperty() {
            // Make sure manager and property details are available
            if (this.manager && this.manager.username) {
                this.updateProperty.manager = this.manager;  // Set manager's username
            } else {
                alert("Manager is not available");
                return;
            }

            axios.put(propertiesApiAddress(this.updateProperty.address), this.updateProperty)
                .then(() => {
                    window.location = 'view-properties.html';
                })
                .catch(error => {
                    alert(error.response.data.message);
                });
        },

        deleteProperty(){
        // Show dialog to confirm deletion ok to delete and cancel doesn't delete
            if (!confirm('Are you sure you want to delete this property?')) {
                return;
            }
            axios.delete(propertiesApiAddress(this.updateProperty.address))
                .then(() => {
                    window.location = 'view-properties.html';
                })
                .catch(error => {
                    alert(error.response.data.message);
                });
        },

        requestWork(){
            window.location = "management.html";
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
