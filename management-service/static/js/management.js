/* global Vue, axios */
var propertiesApi = '/api/properties';
var requestApiTenant = (username) => `/api/requests/${username}`;
var managementApi = '/api/management';

const app = Vue.createApp({

    data() {
        return {
            management: new Object(),
            requests: [],
            requestNumber: null,
            manageProperty: new Object(),
        };
    },

    computed: {
        // Map Vuex state to get the tenant from the data store
        ...Vuex.mapState({
            manager: state => state.manager,
            property : state => state.property
        }),
    },

    mounted() {
        console.log("Manager in Vuex:", this.manager);
        console.log("Property in Vuex:", this.property);
        this.fetchRequests();
        this.fetchNumberOfRequests(this.requests);
    },

    methods: {
        fetchRequests() {
            // Make sure manager and property details are available
            console.log(this.property.tenantUsername);
            if (this.manager && this.property && this.property.tenantUsername) {
                axios.get(requestApiTenant(this.property.tenantUsername))
                    .then(response => {
                        this.requests = response.data;
                        console.log(this.requests);
                        this.fetchNumberOfRequests(); // Update requestNumber after requests are fetched
                    })
                    .catch(error => {
                        alert("An error occurred - check the console for details.");
                        console.error(error);
                    });
            } else {
                alert("Manager or property details are not available");
            }
        },

        fetchNumberOfRequests() {
            this.requestNumber = this.requests.length;
            console.log('Number of requests:', this.requestNumber); // Debugging log
        },

        createJob() {
            // Assign property to management.property
            this.management.property = this.property;
            console.log(this.management);
            axios.post(managementApi, this.management)
                .then(() => {
                    window.location = 'view-properties.html';
                })
                .catch(error => {
                    alert(error.response.data.message);
                });
        }
    },

    mixins: []
});

// other component imports go here

import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);

import { dataStore } from './data-store.js';
app.use(dataStore);

// mount the page - this needs to be the last line in the file
app.mount("main");
