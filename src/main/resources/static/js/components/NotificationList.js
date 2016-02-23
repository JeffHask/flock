/**
 * Created by John on 2/11/2016.
 */

var NotificationList = React.createClass({
    mixins: [Reflux.connect(NotificationStore,'notifications')],
    getInitialState: function() {
        return {notifications : undefined};
    },
    componentDidMount: function() {
        console.log('componentMounted');
        NotificationActions.fetchNotifications();
    },
    render: function() {
        console.log('rendering');
        if(this.state.notifications === undefined) {
            return <div> Loading <i className="fa fa-spin fa-refresh -align-center"/> </div>;
        }

        var notificationNodes = this.state.notifications.map(function (notification){
            return <tr key={notification.id}>  <td>{notification.type}</td><td>{notification.notificationName}</td></tr>});

        if(notificationNodes.length === 0) {
            return(<div className="text-center">You have no notifications!</div>)
        }
        return (
            <div>
                <NavBar/>
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th>
                            Type
                        </th>
                        <th>
                            Name
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {notificationNodes}
                    </tbody>
                </table>
            </div>)
    }
});
ReactDOM.render(<NotificationList />, document.getElementById("list_notification"));