var NotificationList = React.createClass({
    mixins: [Reflux.connect(NotificationStore, 'notifications')],
    getInitialState: function () {
        return {notifications: undefined};
    },
    componentDidMount: function () {
        NotificationActions.fetchNotifications();
    },
    handleDelete: function (id) {
        NotificationActions.deleteNotification(id);
    },
    handleAccept: function (id) {
        ConnectionActions.acceptConnection(id);
    },

    render: function () {
        var notificationNodes;
        if (this.state.notifications === undefined) {
            return <div> Loading <i className="fa fa-spin fa-refresh align-center"/></div>;
        }
        var parent = this;
        notificationNodes = this.state.notifications.map(function (notification) {
            console.log(notification);
            var handleClick = parent.handleDelete.bind(parent, notification.id);
            var handleAcceptClick = parent.handleAccept.bind(parent, notification.creator.id);
            var buttonType;

            if (notification.type === 2) {
                buttonType = (<button type="button" className="btn btn-lg btn-success" onClick={handleAcceptClick}>
                    Accept</button>);
            }
            return (<tr key={notification.id}>
                <td>{notification.message}</td>
                <td>
                    <a>{buttonType}</a>
                    <a className="btn btn-info active btn-lg" href={notification.url} role="button">View</a>
                    <button id={notification.id} key={notification.id} type="button" className="btn btn-lg btn-danger"
                            onClick={handleClick}> Delete
                    </button>
                </td>
            </tr>);
        });

        if (notificationNodes.length === 0) {
            return (<div className="text-center">You have no notifications!</div>)
        }
        //$.bootstrapSortable()   add this table later after backend works.
        return (
            <table className="table table-hover">
                <tbody>
                {notificationNodes}
                </tbody>
            </table>
        );
    }
});